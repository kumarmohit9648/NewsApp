package com.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.appintro.SlidePolicy
import com.newsapp.R

class NewsPostIntroFragment : Fragment(), SlidePolicy {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_post_intro, container, false)
    }

    override val isPolicyRespected: Boolean
        get() = true

    override fun onUserIllegallyRequestedNextPage() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsPostIntroFragment()
    }
}