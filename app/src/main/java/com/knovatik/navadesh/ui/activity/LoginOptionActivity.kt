package com.knovatik.navadesh.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityLoginOptionBinding
import com.knovatik.navadesh.model.social.SocialLoginRequest
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.vm.LoginOptionViewModel
import com.knovatik.navadesh.util.snackbar
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class LoginOptionActivity : BaseActivity() {

    companion object {
        private const val TAG = "LoginOptionActivity"
        private const val RC_SIGN_IN = 242
    }

    private lateinit var binding: ActivityLoginOptionBinding
    private val viewModel: LoginOptionViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var loginManager: LoginManager? = null
    private var callbackManager: CallbackManager? = null
    private lateinit var auth: FirebaseAuth

    private var isSocial = false

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

        viewModel.socialRegistrationResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data != null) {
                        if (it.data.token.isNullOrEmpty()) {
                            startActivity(
                                Intent(this@LoginOptionActivity, RegisterActivity::class.java)
                                    .putExtra(AppConstant.IS_SOCIAL, true)
                                    .putExtra(AppConstant.USERNAME, it.data.username)
                                    .putExtra(AppConstant.EMAIL, it.data.email)
                            )
                        } else {
                            Prefs.putString(AppConstant.AUTH_TOKEN, it.data.token)
                            Prefs.putBoolean(AppConstant.IS_LOGIN, true)
                            startActivity(
                                Intent(
                                    this@LoginOptionActivity,
                                    DashboardActivity::class.java
                                )
                            )
                            finishAffinity()
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        })

    }

    private fun apiSocialLogin(username: String, email: String) {
        viewModel.socialRegistration(
            SocialLoginRequest(
                user_email = email,
                user_name = username
            )
        )
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun startSocialSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.google_client_id))
            .build()
        auth = Firebase.auth
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        callbackManager = CallbackManager.Factory.create()
        loginManager = LoginManager.getInstance()
        loginManager?.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {


            override fun onCancel() {
                //if (social_progress != null) social_progress.dismiss()
                Log.e(TAG, "facebook cancelled")
            }

            override fun onError(exception: FacebookException?) {
                // if (social_progress != null) social_progress.dismiss()
                //              System.out.println("onError");
                Log.v(TAG, "facebook login error: " + exception?.localizedMessage)
            }

            override fun onSuccess(loginResult: LoginResult?) {
                //if (social_progress != null) social_progress.dismiss()
                val accessToken: String = loginResult?.accessToken!!.token
                val request: GraphRequest =
                    GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
                        Log.i(TAG, response.toString())
                        val bFacebookData: Bundle = getFacebookData(`object`)
                        // Log.d("Email: "+bFacebookData.getString("email"));
                        loginFromFacebook(bFacebookData, accessToken)
                    }
                val parameters = Bundle()
                parameters.putString(
                    "fields",
                    "id, first_name, last_name, email,gender, birthday, location"
                ) // Parámetros que pedimos a facebook
                request.parameters = parameters
                request.executeAsync()
            }
        })

    }

    private fun signInGoogle() {
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
                    apiSocialLogin(username = user?.displayName!!, email = user.email!!)
                    // updateUI(user)
                } else {
                    Log.w(TAG, "firebaseAuthWithGoogle: ", task.exception)
                    binding.root.snackbar("Authentication Failed.")
                }
            }
    }

    private fun signInFacebook() {
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
            apiSocialLogin(username = map["name"]!!, email = map["email"]!!)
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
            val profilePicture = "https://graph.facebook.com/$id/picture?width=200&height=150"
            Log.i("profile_pic %s", profilePicture)
            bundle.putString("profile_pic", profilePicture.toString())
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
        callbackManager?.onActivityResult(requestCode, resultCode, data);
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