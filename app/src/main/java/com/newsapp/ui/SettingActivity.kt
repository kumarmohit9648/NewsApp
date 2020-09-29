package com.newsapp.ui

import android.os.Bundle
import com.newsapp.R
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