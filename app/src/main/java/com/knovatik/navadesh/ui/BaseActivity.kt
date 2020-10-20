package com.knovatik.navadesh.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.ui.activity.OpenFragmentActivity

abstract class BaseActivity : AppCompatActivity() {

    fun openFragment(fragmentId: String, title: String) {
        startActivity(
            Intent(this, OpenFragmentActivity::class.java)
                .putExtra(AppConstant.FRAGMENT_ID, fragmentId)
                .putExtra(AppConstant.FRAGMENT_TITLE, title)
        )
    }

}