package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityLoginOptionBinding
import com.newsapp.ui.BaseActivity
import com.newsapp.util.snackbar
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import org.json.JSONObject

class LoginOptionActivity : BaseActivity() {

    companion object {
        private const val TAG = "LoginOptionActivity"
        private const val GOOGLE_SIGN_IN = 99
        private const val RC_SIGN_IN = 242
    }

    private lateinit var binding: ActivityLoginOptionBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var loginManager: LoginManager? = null
    var callbackManager: CallbackManager? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Prefs.getBoolean(AppConstant.IS_LOGIN, false)) {
            startActivity(Intent(this@LoginOptionActivity, DashboardActivity::class.java))
            finishAffinity()
        }

        startSocialSignIn()

        binding.btnGoogle.setOnClickListener {
            signInGoogle()
        }

        binding.btnFacebook.setOnClickListener {
            signInFacebook()
        }

        binding.btnMobile.setOnClickListener {
            startActivity(Intent(this@LoginOptionActivity, LoginActivity::class.java))
        }

        binding.btnSkip.setOnClickListener {
            goToAppIntro()
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginOptionActivity, RegisterActivity::class.java))
        }

    }

    fun startSocialSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()
        loginManager?.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {


            override fun onCancel() {
                //if (social_progress != null) social_progress.dismiss()
                Log.e(TAG, "facebook cancelled")
            }

            override fun onError(exception: FacebookException?) {
                //if (social_progress != null) social_progress.dismiss()
                //              System.out.println("onError");
                Log.v(TAG, "facebook login error: " + exception?.localizedMessage)
            }

            override fun onSuccess(loginResult: LoginResult?) {
                //if (social_progress != null) social_progress.dismiss()
                val accessToken: String = loginResult?.accessToken!!.token
                val request: GraphRequest =
                    GraphRequest.newMeRequest(loginResult.getAccessToken()) { `object`, response ->
                        Log.i(TAG, response.toString())
                        val bFacebookData: Bundle = getFacebookData(`object`)
                        //Log.d("Email: "+bFacebookData.getString("email"));
                        loginFromFacebook(bFacebookData, accessToken)
                    }
                val parameters = Bundle()
                parameters.putString(
                    "fields",
                    "id, first_name, last_name, email,gender, birthday, location"
                ) // Parámetros que pedimos a facebook
                request.setParameters(parameters)
                request.executeAsync()
            }
        })

    }

    fun signInGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    Log.w(TAG, "firebaseAuthWithGoogle: ", task.exception)
                    binding.root.snackbar("Authentication Failed.")
                }
            }
    }

    fun signInFacebook() {
        loginManager?.logInWithReadPermissions(this, listOf("email"));
    }

    private fun loginFromFacebook(bFacebookData: Bundle, token: String) {
        val map: MutableMap<String, String> = mutableMapOf()
        try {
            map["from"] = "fb"
            map["id"] = bFacebookData.getString("idFacebook")!!
            map["name"] =
                bFacebookData.getString("first_name")!! + " " + bFacebookData.getString("last_name")!!
            map["email"] = bFacebookData.getString("email")!!
            map["idToken"] = token
            map["imgURL"] = bFacebookData.getString("profile_pic")!!
            Log.d(TAG, "Map: $map")
            loginFromSocial(map)
        } catch (e: java.lang.Exception) {
            toast("Social Login Failed")
            Log.e("SOCIAL LOGIN ERROR: %s", e.localizedMessage)
            e.printStackTrace()
        }
    }

    private fun getFacebookData(`object`: JSONObject): Bundle {
        val bundle = Bundle()
        try {
            val id = `object`.getString("id")
            val profile_pic = "https://graph.facebook.com/$id/picture?width=200&height=150"
            Log.i("profile_pic %s", profile_pic)
            bundle.putString("profile_pic", profile_pic.toString())
            bundle.putString("idFacebook", id)
            if (`object`.has("first_name")) bundle.putString(
                "first_name",
                `object`.getString("first_name")
            )
            if (`object`.has("last_name")) bundle.putString(
                "last_name",
                `object`.getString("last_name")
            )
            if (`object`.has("email")) bundle.putString("email", `object`.getString("email"))
            if (`object`.has("gender")) bundle.putString("gender", `object`.getString("gender"))
            if (`object`.has("birthday")) bundle.putString(
                "birthday",
                `object`.getString("birthday")
            )
            if (`object`.has("location")) bundle.putString(
                "location",
                `object`.getJSONObject("location").getString("name")
            )

        } catch (e: Exception) {
            Log.d(TAG, String.format("Error while getting facebook data: %s", e.localizedMessage))
        }
        return bundle
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, String.format("firebaseAuthWithGoogle: %s", account.id))
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "onActivityResult: ", e)
            }
        }
    }

    private fun loginFromSocial(jsonBody: Map<String, String>) {/*
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wwait")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val url = "/api/v1/auth/loginsignup/fb/go/st1"
        val apiService: ApiInterface =
            ApiClient.getInstance().getClient(false).create(ApiInterface::class.java)
        val call: Call<String> = apiService.makePostRequestForm(url, jsonbody)
        call.enqueue(object : Callback<String?>() {
            fun onResponse(@NonNull call: Call<String?>?, @NonNull response: Response<String?>) {
                Log.d("Response login social: %s", response.body())
                progressDialog.dismiss()
                Log.d("Social Login response:   %s", response.body())
                val response1: com.app.edugorillatestseries.Modals.Response =
                    ApiClient.getResponseModal(response.body())
                try {
                    //JSONObject jsonObject = new JSONObject(response1.getResult().getData());
                    if (response1.getStatus()) {
                        val data: String = response1.getResult().getData()
                        val data_ = JSONObject(data)
                        //if (data_.getString("type").equals("login")) {
                        val rawCookies: String = response.headers().get("X-Device-Logged-In-Auth")
                        Log.d("cookies %s", rawCookies)
                        if (rawCookies != null) {
                            Prefs.putString(C.KEY_COOKIE, rawCookies)
                            Prefs.putBoolean(C.KEY_IS_LOGGED_IN, true)
                        }
                        getProfileCard()
                        *//* } else if (data_.getString("type").equals("signup")) {
                            String token = data_.getString("token");
                            String rawCookies = response.headers().get("X-Device-Signup-Auth");
                            startActivity(new Intent(context, VerificationActivity.class).putExtra("token", token).putExtra("mode", VerificationActivity.MODE_SOCIAL_LOGIN).putExtra("X-Device-Signup-Auth", rawCookies));
                        }*//*
                    } else {
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            fun onFailure(@NonNull call: Call<String?>?, @NonNull t: Throwable) {
                progressDialog.dismiss()
                Utils.showSnackBarLong(login, t.localizedMessage + " Please try again!")
                Log.e("Social Login Error %s", t.localizedMessage)
            }
        })*/
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null
        val account = GoogleSignIn.getLastSignedInAccount(this)
        // updateUI(account)
    }

    private fun goToAppIntro() {
        startActivity(Intent(this@LoginOptionActivity, DashboardActivity::class.java))
        finishAffinity()
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)!!

            // Signed in successfully, show authenticated UI.
            startActivity(Intent(this@LoginOptionActivity, AppIntroActivity::class.java))
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code =" + e.statusCode)
            Toast.makeText(this@LoginOptionActivity, "Unable to authenticate", Toast.LENGTH_SHORT)
                .show()
        }
    }

}