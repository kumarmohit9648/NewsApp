package com.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.newsapp.R
import com.newsapp.ui.adapter.NewsFeedAdapter
import kotlinx.android.synthetic.main.fragment_tab.*

class TabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        val list = ArrayList<String>()

        list.add("Trending")
        list.add("National")
        list.add("International")
        list.add("Technology")
        list.add("Politics")
        list.add("Business")
        list.add("Share Market")
        list.add("Entertainment")
        list.add("Sports")
        list.add("My City")
        list.add("Astrology")
        list.add("Health")
        list.add("Bollywood")
        list.add("Hollywood")
        list.add("Celebrities")
        list.add("Religion")

        recyclerNewsFeed.adapter = NewsFeedAdapter(requireContext(), list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TabFragment()
    }

}