package com.knovatik.navadesh.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knovatik.navadesh.R
import com.knovatik.navadesh.model.NewsType

class NewsTypeAdapter(private val context: Context, private var list: List<NewsType>) :
    RecyclerView.Adapter<NewsTypeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsTypeHolder {
        return NewsTypeHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_news_type,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsTypeHolder, position: Int) {
        /*val model = list[position]
        holder.itemView.textView.text = model.name

        if (model.isAdded) {
            holder.itemView.add_news.setImageResource(R.drawable.ic_plus)
            // Glide.with(context).load(R.drawable.ic_plus).into(holder.itemView.add_news)
        } else {
            holder.itemView.add_news.setImageResource(R.drawable.ic_add)
            // Glide.with(context).load(R.drawable.ic_add).into(holder.itemView.add_news)
        }

        holder.itemView.add_news.setOnClickListener {
            if (!model.isAdded) {
                list[position].isAdded = true
                holder.itemView.add_news.setImageResource(R.drawable.ic_plus)
                // Glide.with(context).load(R.drawable.ic_plus).into(holder.itemView.add_news)
            } else {
                list[position].isAdded = false
                holder.itemView.add_news.setImageResource(R.drawable.ic_add)
                // Glide.with(context).load(R.drawable.ic_add).into(holder.itemView.add_news)
            }
        }*/
    }

    override fun getItemCount() = list.size

}

class NewsTypeHolder(itemView: View) : RecyclerView.ViewHolder(itemView)