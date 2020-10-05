package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        ivBack.setOnClickListener {
            finish()
        }
    }
}