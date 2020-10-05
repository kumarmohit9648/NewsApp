package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setData()

    }

    private fun setData() {
        /*val list = ArrayList<NewsType>()

        list.add(NewsType("Trending", false))
        list.add(NewsType("National", false))
        list.add(NewsType("International", true))
        list.add(NewsType("Technology", false))
        list.add(NewsType("Politics", false))
        list.add(NewsType("Business", false))
        list.add(NewsType("Share Market", true))
        list.add(NewsType("Entertainment", false))
        list.add(NewsType("Sports", false))
        list.add(NewsType("My City", false))
        list.add(NewsType("Astrology", false))
        list.add(NewsType("Health", false))
        list.add(NewsType("Bollywood", false))
        list.add(NewsType("Hollywood", false))
        list.add(NewsType("Celebrities", false))
        list.add(NewsType("Religion", false))

        recyclerNewsType.adapter = NewsTypeAdapter(this@FilterActivity, list)*/

    }

}