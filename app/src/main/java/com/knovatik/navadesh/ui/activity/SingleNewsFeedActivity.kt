package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.knovatik.navadesh.R
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.adapter.SingleNewsFeedAdapter
import kotlinx.android.synthetic.main.activity_single_news_feed.*

class SingleNewsFeedActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_news_feed)

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerSingleNewsFeed)

        recyclerSingleNewsFeed.adapter =
            SingleNewsFeedAdapter(this@SingleNewsFeedActivity, supportFragmentManager)
    }

}