package com.newsapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.newsapp.R
import com.newsapp.ui.adapter.SingleNewsFeedAdapter
import kotlinx.android.synthetic.main.activity_single_news_feed.*

class SingleNewsFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news_feed)

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerSingleNewsFeed)

        recyclerSingleNewsFeed.adapter =
            SingleNewsFeedAdapter(this@SingleNewsFeedActivity, supportFragmentManager)
    }

}