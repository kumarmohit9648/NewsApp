package com.knovatik.navadesh.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.knovatik.navadesh.R

class SingleNewsFeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_news_feed, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SingleNewsFeedFragment()
    }

}