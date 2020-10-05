package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.R
import kotlinx.android.synthetic.main.activity_login_option.*

class LoginOptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_option)

        btnGoogle.setOnClickListener {

        }

        btnFacebook.setOnClickListener {

        }

        btnMobile.setOnClickListener {

        }

        btnSkip.setOnClickListener {
            goToAppIntro()
        }

    }

    private fun goToAppIntro() {
        startActivity(Intent(this@LoginOptionActivity, AppIntroActivity::class.java))
    }

}