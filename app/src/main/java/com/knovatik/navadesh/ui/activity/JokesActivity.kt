package com.knovatik.navadesh.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityJokesBinding
import com.knovatik.navadesh.model.section.SectionItemRequest
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.adapter.JokesAdapter
import com.knovatik.navadesh.ui.vm.JokesViewModel
import com.knovatik.navadesh.util.CustomJzvd.JzvdStdTikTok
import com.knovatik.navadesh.util.CustomJzvd.OnViewPagerListener
import com.knovatik.navadesh.util.CustomJzvd.ViewPagerLayoutManager
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokesActivity : BaseActivity() {

    companion object {
        private const val TAG = "JokesActivity"
        var mediaPlayer: MediaPlayer? = null
    }

    private lateinit var binding: ActivityJokesBinding
    private val viewModel: JokesViewModel by viewModels()
    private var _currentPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var titleName = intent.getStringExtra(AppConstant.SECTION_NAME)
        var sectionId = intent.getStringExtra(AppConstant.SECTION_ID)
        if (titleName == null)
            titleName = "गुब्बारे"
        if (sectionId == null)
            sectionId = "3"

        binding.toolbar.titleName.text = titleName

        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        setData(sectionId)

        val viewPagerLayoutManager = ViewPagerLayoutManager(this, OrientationHelper.VERTICAL)
        val linearLayoutManager = LinearLayoutManager(this)

        viewModel.getSectionItemResponse.observe(this, {
            if (it.status) {
                if (it.data != null) {
                    if (sectionId == "3" || sectionId == "4")
                        binding.recyclerJokes.layoutManager = viewPagerLayoutManager
                    else
                        binding.recyclerJokes.layoutManager = linearLayoutManager
                    binding.recyclerJokes.adapter = JokesAdapter(this, it.data)
                }
            } else
                toast(it.message)
        })

        viewPagerLayoutManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                autoPlayVideo(0)
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                if (_currentPosition == position) {
                    Jzvd.releaseAllVideos()
                }
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                if (_currentPosition == position) {
                    return
                }
                autoPlayVideo(position)
                _currentPosition = position
            }
        })

        binding.recyclerJokes.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {

            }

            override fun onChildViewDetachedFromWindow(view: View) {

            }

        })

    }

    private fun setData(sectionId: String) {
        viewModel.getSectionItem(
            SectionItemRequest(
                Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                sectionId
            )
        )
    }

    private fun autoPlayVideo(position: Int) {
        if (binding.recyclerJokes.getChildAt(0) == null) {
            return
        }
        val player: JzvdStdTikTok =
            binding.recyclerJokes.getChildAt(0).findViewById(R.id.videoPlayer)
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

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
    }
}