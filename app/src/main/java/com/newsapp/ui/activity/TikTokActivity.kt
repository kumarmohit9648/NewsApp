package com.newsapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.newsapp.R
import com.newsapp.databinding.ActivityTikTokBinding
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.TikTokAdapter
import com.newsapp.util.CustomJzvd.JzvdStdTikTok
import com.newsapp.util.CustomJzvd.OnViewPagerListener
import com.newsapp.util.CustomJzvd.ViewPagerLayoutManager

class TikTokActivity : BaseActivity() {

    private lateinit var binding: ActivityTikTokBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTikTokBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TikTokAdapter(this, null)
        val viewPagerLayoutManager = ViewPagerLayoutManager(this, OrientationHelper.VERTICAL)
        binding.rvTiktok.layoutManager = viewPagerLayoutManager
        binding.rvTiktok.adapter = adapter

        viewPagerLayoutManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {

            }

            override fun onPageRelease(isNext: Boolean, position: Int) {

            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {

            }
        })

        binding.rvTiktok.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {

            }

            override fun onChildViewDetachedFromWindow(view: View) {

            }

        })
    }

    private fun autoPlayVideo(position: Int) {
        if (binding.rvTiktok.getChildAt(0) == null) {
            return
        }
        val player: JzvdStdTikTok = binding.rvTiktok.getChildAt(0).findViewById(R.id.videoPlayer)
        player.startVideoAfterPreloading()
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

}