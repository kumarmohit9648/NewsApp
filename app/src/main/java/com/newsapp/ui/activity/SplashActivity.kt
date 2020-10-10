package com.newsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        imageView.postDelayed({
            startActivity(Intent(this@SplashActivity, AppIntroActivity::class.java))
            finishAffinity()
        }, 0)

    }
}