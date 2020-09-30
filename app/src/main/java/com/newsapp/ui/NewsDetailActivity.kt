package com.newsapp.ui

import android.os.Bundle
import com.newsapp.R
import kotlinx.android.synthetic.main.toolbar.*

class NewsDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        titleName.text = "News details"

        ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }
    }
}