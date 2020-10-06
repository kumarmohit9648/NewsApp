package com.newsapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.newsapp.R
import com.newsapp.ui.fragment.SingleNewsFeedFragment
import kotlinx.android.synthetic.main.recycler_single_news_feed.view.*
import java.util.*

class SingleNewsFeedAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager,
    private var list: List<String>? = null
) :
    RecyclerView.Adapter<SingleNewsFeedHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleNewsFeedHolder {
        return SingleNewsFeedHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_single_news_feed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SingleNewsFeedHolder, position: Int) {
        val model = list?.get(position)
        setUpViewPager(holder.itemView.viewPager)
    }

    override fun getItemCount() = 10

    private fun setUpViewPager(viewpager: ViewPager) {
        val adapter = ViewPagerAdapter(fragmentManager)
        // adapter.addFrag(EmptyFragment(), "Empty")
        adapter.addFrag(SingleNewsFeedFragment(), "SingleNewsFeed")
        // adapter.addFrag(EmptyFragment(), "Empty")
        viewpager.adapter = adapter
        // viewpager.currentItem = 1
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(
            fragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {

        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFrag(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

    }

}

class SingleNewsFeedHolder(itemView: View) : RecyclerView.ViewHolder(itemView)