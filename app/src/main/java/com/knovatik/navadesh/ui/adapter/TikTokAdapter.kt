package com.knovatik.navadesh.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knovatik.navadesh.R

class TikTokAdapter(
    private var context: Context,
    private var list: List<String>?
) : RecyclerView.Adapter<VideoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        return VideoHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_video,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        // val data = list[position]
        // Glide.with(context).load(data.profile_image).into(holder.itemView.circleImageView)
        // holder.itemView.user_name.text = data.user_name
        // holder.itemView.user_comment.text = data.comment
    }

}