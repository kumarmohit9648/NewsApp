package com.knovatik.navadesh.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.knovatik.navadesh.OnSingleItemClickListener
import com.knovatik.navadesh.R
import com.knovatik.navadesh.model.notification.NotificationList
import com.knovatik.navadesh.network.utils.Coroutines
import kotlinx.android.synthetic.main.recycler_notification.view.*

class NotificationAdapter(
    private val onSingleItemClickListener: OnSingleItemClickListener,
    private var list: List<NotificationList>
) : RecyclerView.Adapter<NotificationHolder>() {

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


        holder.itemView.cardView.setOnClickListener {
            Coroutines.main {
                list[position].is_read = "1"
                notifyDataSetChanged()
            }
            onSingleItemClickListener.onClickPosition(position)
        }
    }

    override fun getItemCount() = list.size

}

class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView)