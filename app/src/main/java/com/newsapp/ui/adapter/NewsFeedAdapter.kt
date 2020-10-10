package com.newsapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.model.posts.Data
import com.newsapp.ui.activity.NewsDetailActivity
import kotlinx.android.synthetic.main.recycler_news_feed.view.*

class NewsFeedAdapter(private val context: Context, private var list: List<Data>) :
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

        Glide.with(context).load("http://dbpnews.knovatik.com/" + model.image_big)
            .into(holder.itemView.imageView)
        holder.itemView.textView3.text = model.title

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