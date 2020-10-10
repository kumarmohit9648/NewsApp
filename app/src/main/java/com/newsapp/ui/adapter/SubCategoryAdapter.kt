package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import com.newsapp.model.submenu.Data
import kotlinx.android.synthetic.main.recycler_sub_category.view.*

class SubCategoryAdapter(
    private var context: Context,
    private var list: List<Data>
) : RecyclerView.Adapter<SubCategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryHolder {
        return SubCategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_sub_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubCategoryHolder, position: Int) {
        val data = list[position]
        holder.itemView.stateName.text = data.name

        holder.itemView.setOnClickListener {
            // context.startActivity(Intent(context))
        }
    }

    override fun getItemCount(): Int = list.size

}

class SubCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)