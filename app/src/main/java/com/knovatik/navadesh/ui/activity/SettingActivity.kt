package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import com.knovatik.navadesh.R
import com.knovatik.navadesh.ui.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        titleName.text = "सेटिंग"

        ivBack.setOnClickListener {
            finish()
        }
    }
}