package com.newsapp.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import com.newsapp.R
import com.newsapp.ui.activity.JokesActivity
import com.newsapp.ui.activity.TikTokActivity
import kotlinx.android.synthetic.main.fragment_time_pass.*

class TimePassFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = TimePassFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvJokes.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), JokesActivity::class.java))
        }
        cvMusic.setOnClickListener {

        }
        cvVideo.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), TikTokActivity::class.java))
        }
        cvPopular.setOnClickListener {

        }
        cvBhakti.setOnClickListener {

        }
        cvFilmy.setOnClickListener {

        }
        cvAstrology.setOnClickListener {

        }
        cvRadio.setOnClickListener {
            loadUrl("http://radio.garden/")
        }
        cvLifestyle.setOnClickListener {

        }
    }

    private fun loadUrl(url: String) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url));
    }

}