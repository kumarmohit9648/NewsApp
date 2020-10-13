package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.ui.BaseActivity
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_login_option.*

class LoginOptionActivity : BaseActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_option)

        if (Prefs.getBoolean(AppConstant.IS_LOGIN, false)) {
            startActivity(Intent(this@LoginOptionActivity, DashboardActivity::class.java))
            finishAffinity()
        }

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnGoogle.setOnClickListener {
            signIn()
        }

        btnFacebook.setOnClickListener {

        }

        btnMobile.setOnClickListener {
            startActivity(Intent(this@LoginOptionActivity, LoginActivity::class.java))
        }

        btnSkip.setOnClickListener {
            goToAppIntro()
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginOptionActivity, RegisterActivity::class.java))
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...)
        if (requestCode == GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    companion object {
        private const val TAG = "LoginOptionActivity"
        private const val GOOGLE_SIGN_IN = 99
    }

}