package com.knovatik.navadesh.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knovatik.navadesh.R
import com.knovatik.navadesh.model.media.Media

class MediaAdapter(
    private var context: Context,
    private var list: List<Media>
) : RecyclerView.Adapter<MediaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        return MediaHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_comment,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        val data = list[position]
        // Glide.with(context).load(data.profile_image).into(holder.itemView.circleImageView)
        // holder.itemView.user_name.text = data.user_name
    }

}

class MediaHolder(itemView: View) : RecyclerView.ViewHolder(itemView)