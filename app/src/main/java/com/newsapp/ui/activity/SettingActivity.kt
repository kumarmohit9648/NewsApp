package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        titleName.text = "Settings"

        ivBack.setOnClickListener {
            finish()
        }
    }
}