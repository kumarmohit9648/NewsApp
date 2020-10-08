package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.model.State
import kotlinx.android.synthetic.main.recycler_state.view.*

class StateAdapter(
    private var context: Context,
    private var list: List<State>
) : RecyclerView.Adapter<StateHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateHolder {
        return StateHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_state,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StateHolder, position: Int) {
        val data = list[position]
        holder.itemView.stateName.text = data.state_name
        // Glide.with(context).load(data.image).into(holder.itemView.stateImage)
    }

    override fun getItemCount(): Int = list.size

}

class StateHolder(itemView: View) : RecyclerView.ViewHolder(itemView)