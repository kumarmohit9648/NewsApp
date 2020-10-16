package com.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.model.section.Data
import kotlinx.android.synthetic.main.recycler_jokes.view.*

class JokesAdapter(
    private var context: Context,
    private var list: List<Data>
) : RecyclerView.Adapter<JokesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesHolder {
        return JokesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_jokes,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: JokesHolder, position: Int) {
        val data = list[position]
        holder.itemView.view.setBackgroundColor(Color.parseColor(data.color))
        holder.itemView.tvJokes.text = HtmlCompat.fromHtml(
            data.content,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        holder.itemView.ivShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, data.link)
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

}

class JokesHolder(itemView: View) : RecyclerView.ViewHolder(itemView)