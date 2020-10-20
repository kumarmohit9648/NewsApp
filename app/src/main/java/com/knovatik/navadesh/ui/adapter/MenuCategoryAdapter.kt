package com.knovatik.navadesh.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.model.menu.Data
import com.knovatik.navadesh.ui.activity.OpenFragmentActivity
import kotlinx.android.synthetic.main.recycler_menu_category.view.*

class MenuCategoryAdapter(
    private var context: Context,
    private var list: List<Data>
) : RecyclerView.Adapter<MenuCategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuCategoryHolder {
        return MenuCategoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_menu_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MenuCategoryHolder, position: Int) {
        val model = list[position]
        holder.itemView.newsMenuCategory.text = model.name
        Glide.with(context).load(model.icon).apply(RequestOptions().fitCenter()).into(
            object : CustomTarget<Drawable>(50, 50) {
                override fun onLoadCleared(placeholder: Drawable?) {
                    holder.itemView.newsMenuCategory.setCompoundDrawablesWithIntrinsicBounds(
                        placeholder,
                        null,
                        null,
                        null
                    )
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.itemView.newsMenuCategory.setCompoundDrawablesWithIntrinsicBounds(
                        resource,
                        null,
                        null,
                        null
                    )
                }
            }
        )

        holder.itemView.newsMenuCategory.setOnClickListener {
            when (model.is_menu) {
                "1" -> {
                    context.startActivity(
                        Intent(context, OpenFragmentActivity::class.java)
                            .putExtra(AppConstant.FRAGMENT_ID, AppConstant.FRAGMENT_SUB_CATEGORY)
                            .putExtra(AppConstant.FRAGMENT_TITLE, model.name)
                            .putExtra(AppConstant.CATEGORY_ID, model.id)
                    )
                }
                "2" -> {
                    context.startActivity(
                        Intent(context, OpenFragmentActivity::class.java)
                            .putExtra(AppConstant.FRAGMENT_ID, AppConstant.FRAGMENT_TIME_PASS)
                            .putExtra(AppConstant.FRAGMENT_TITLE, model.name)
                            .putExtra(AppConstant.CATEGORY_ID, model.id)
                    )
                }
                "3" -> {
                    context.startActivity(
                        Intent(context, OpenFragmentActivity::class.java)
                            .putExtra(
                                AppConstant.FRAGMENT_ID,
                                AppConstant.FRAGMENT_CITIZEN_REPORTER
                            )
                            .putExtra(AppConstant.FRAGMENT_TITLE, model.name)
                            .putExtra(AppConstant.CATEGORY_ID, model.id)
                    )
                }
                else -> {
                    context.startActivity(
                        Intent(context, OpenFragmentActivity::class.java)
                            .putExtra(AppConstant.FRAGMENT_ID, AppConstant.FRAGMENT_OTHER)
                            .putExtra(AppConstant.FRAGMENT_TITLE, model.name)
                            .putExtra(AppConstant.CATEGORY_ID, model.id)
                    )
                }
            }
        }

    }

    override fun getItemCount(): Int = list.size
}

class MenuCategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)