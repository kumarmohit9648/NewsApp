package com.newsapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityNewsDetailBinding
import com.newsapp.model.like.LikeRequest
import com.newsapp.model.posts.PostDetailRequest
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.CommentAdapter
import com.newsapp.ui.dialog.SocialShareDialog
import com.newsapp.ui.vm.NewsDetailViewModel
import com.newsapp.util.likeCountFormat
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityNewsDetailBinding
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var newsId = intent.getStringExtra(AppConstant.VIDEO_ID)
        if (newsId == null)
            newsId = ""

        binding.toolbar.titleName.text = "न्यूज़ डिटेल्स"

        binding.toolbar.ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }

        binding.ivShare.setOnClickListener {
            val socialShareDialog = SocialShareDialog(this@NewsDetailActivity)
            socialShareDialog.show()
        }

        setData(newsId)

        binding.likeLayout.setOnClickListener {
            changeLikeStatus(newsId, "1")
        }

        binding.dislikeLayout.setOnClickListener {
            changeLikeStatus(newsId, "-1")
        }

    }

    private fun changeLikeStatus(newsId: String, status: String) {
        viewModel.savePostLikeStatusResponse.observe(this, {
            if (it.status) {
                if (it.post_detail != null)
                    changeStateOfLikeNDislike(
                        it.request_status,
                        it.post_detail.like_count,
                        it.post_detail.dislike_count
                    )
            } else {
                toast(it.message)
            }
        })
        viewModel.savePostLikeStatus(
            LikeRequest(
                Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                post_id = newsId,
                status = status
            )
        )
    }

    private fun setData(newsId: String) {
        viewModel.getPostsDetailResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data != null) {
                        Glide.with(this).load("http://dbpnews.knovatik.com/" + it.data.image_big)
                            .into(binding.newsImage)
                        binding.tvHeading.text = it.data.title
                        binding.tvDetails.text = HtmlCompat.fromHtml(
                            it.data.content,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                        if (it.data.comment_list.isNotEmpty()) {
                            binding.groupComment.visibility = View.VISIBLE
                            binding.commentRecycler.adapter =
                                CommentAdapter(this@NewsDetailActivity, it.data.comment_list)
                        } else {
                            binding.groupComment.visibility = View.GONE
                        }
                        changeStateOfLikeNDislike(
                            it.data.like_status,
                            it.data.like_count,
                            it.data.dislike_count
                        )
                    }
                } else {
                    toast(it.message)
                }
            } catch (e: Exception) {
            }
        })
        viewModel.getPostsDetail(
            PostDetailRequest(
                newsId,
                Prefs.getString(AppConstant.AUTH_TOKEN, "")
            )
        )
    }

    private fun changeStateOfLikeNDislike(status: String, likeCount: String, dislikeCount: String) {
        binding.likeCount.text = likeCountFormat(likeCount)
        binding.dislikeCount.text = likeCountFormat(dislikeCount)
        when (status) {
            "1" -> {
                Glide.with(this).load(R.drawable.ic_like).into(binding.ivLike)
                Glide.with(this).load(R.drawable.ic_not_like)
                    .into(binding.ivDislike)
            }
            "-1" -> {
                Glide.with(this).load(R.drawable.ic_not_like).into(binding.ivLike)
                Glide.with(this).load(R.drawable.ic_like).into(binding.ivDislike)
            }
            else -> {
                Glide.with(this).load(R.drawable.ic_not_like).into(binding.ivLike)
                Glide.with(this).load(R.drawable.ic_not_like)
                    .into(binding.ivDislike)
            }
        }
    }

}