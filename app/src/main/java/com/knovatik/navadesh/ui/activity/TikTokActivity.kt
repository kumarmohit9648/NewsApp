package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.knovatik.navadesh.R
import com.knovatik.navadesh.databinding.ActivityTikTokBinding
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.adapter.TikTokAdapter
import com.knovatik.navadesh.util.CustomJzvd.JzvdStdTikTok
import com.knovatik.navadesh.util.CustomJzvd.OnViewPagerListener
import com.knovatik.navadesh.util.CustomJzvd.ViewPagerLayoutManager

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