package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.model.posts.Comment
import kotlinx.android.synthetic.main.recycler_comment.view.*

class CommentAdapter(
    private var context: Context,
    private var list: List<Comment>
) : RecyclerView.Adapter<CommentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_comment,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.profile_image).into(holder.itemView.circleImageView)
        holder.itemView.user_name.text = data.user_name
        holder.itemView.user_comment.text = data.comment
    }

}

class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)