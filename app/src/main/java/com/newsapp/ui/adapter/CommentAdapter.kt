package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.model.posts.Comment

class CommentAdapter(
    private var context: Context,
    private var list: List<Comment>?
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

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val data = list?.get(position)
    }

}

class CommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)