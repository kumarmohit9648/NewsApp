package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R

class TikTokAdapter(
    private var context: Context,
    private var list: List<String>?
) : RecyclerView.Adapter<TikTokHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TikTokHolder {
        return TikTokHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_tiktok,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: TikTokHolder, position: Int) {
        // val data = list[position]
        // Glide.with(context).load(data.profile_image).into(holder.itemView.circleImageView)
        // holder.itemView.user_name.text = data.user_name
        // holder.itemView.user_comment.text = data.comment
    }

}

class TikTokHolder(itemView: View) : RecyclerView.ViewHolder(itemView)