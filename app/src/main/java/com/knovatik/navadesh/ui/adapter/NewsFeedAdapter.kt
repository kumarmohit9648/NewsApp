package com.knovatik.navadesh.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.model.posts.Data
import com.knovatik.navadesh.ui.activity.NewsDetailActivity
import kotlinx.android.synthetic.main.recycler_news_feed.view.*

class NewsFeedAdapter(private val context: Context, private var list: MutableList<Data>) :
    RecyclerView.Adapter<NewsFeedHolder>() {

    fun setList(mList: List<Data>) {
        list.clear()
        list.addAll(mList)
        notifyDataSetChanged()
    }

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

        Glide.with(context).load("http://navadesh.com/" + model.image_big)
            .into(holder.itemView.imageView)
        holder.itemView.textView3.text = model.title
        holder.itemView.updatedTime.text = model.aging
        holder.itemView.setOnClickListener {
            val intent =
                context.startActivity(
                    Intent(context, NewsDetailActivity::class.java)
                        .putExtra(AppConstant.VIDEO_ID, model.id)
                )
        }
    }

    override fun getItemCount() = list.size

}

class NewsFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView)