package com.newsapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.ui.NewsDetailActivity
import kotlinx.android.synthetic.main.recycler_news_feed.view.*

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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                holder.itemView.imageView,
                "news_feed"
            )
            context.startActivity(intent, option.toBundle())
        }
    }

    override fun getItemCount() = list.size

}

class NewsFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView)