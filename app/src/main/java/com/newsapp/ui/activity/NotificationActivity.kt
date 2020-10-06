package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class NotificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        titleName.text = "नोटिफिकेशन"

        ivBack.setOnClickListener {
            finish()
        }
    }
}