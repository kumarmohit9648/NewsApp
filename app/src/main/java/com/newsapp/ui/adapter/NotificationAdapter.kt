package com.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.model.notification.NotificationList
import com.newsapp.ui.activity.NewsDetailActivity
import kotlinx.android.synthetic.main.recycler_notification.view.*

class NotificationAdapter(private val context: Context, private var list: List<NotificationList>) :
    RecyclerView.Adapter<NotificationHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewFeed: Int): NotificationHolder {
        return NotificationHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_notification,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val model = list[position]
        holder.itemView.text.text = model.title

        if (model.is_read == "0")
            holder.itemView.text.typeface = Typeface.DEFAULT_BOLD
        else
            holder.itemView.text.typeface = Typeface.DEFAULT


        holder.itemView.setOnClickListener {
            list[position].is_read = "1"
            context.startActivity(
                Intent(context, NewsDetailActivity::class.java)
                    .putExtra(AppConstant.VIDEO_ID, model.id)
            )
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = list.size

}

class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView)