package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class NewsFeedAdapter(private val context: Context, private var list: List<String>) :
    RecyclerView.Adapter<NewsFeedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewFeed: Int): NewsFeedHolder {
        return NewsFeedHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_news_feed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsFeedHolder, position: Int) {
        val model = list[position]
    }

    override fun getItemCount() = list.size

}

class NewsFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView)