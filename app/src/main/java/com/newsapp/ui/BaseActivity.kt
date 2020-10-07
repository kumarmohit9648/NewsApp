package com.newsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.constants.AppConstant
import com.newsapp.ui.activity.OpenFragmentActivity

abstract class BaseActivity : AppCompatActivity() {

    fun openFragment(fragmentId: String, title: String) {
        startActivity(
            Intent(this, OpenFragmentActivity::class.java)
                .putExtra(AppConstant.FRAGMENT_ID, fragmentId)
                .putExtra(AppConstant.FRAGMENT_TITLE, title)
        )
    }

}