package com.newsapp.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityNewsDetailBinding
import com.newsapp.model.comment.CommentRequest
import com.newsapp.model.like.LikeRequest
import com.newsapp.model.posts.PostDetailRequest
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.CommentAdapter
import com.newsapp.ui.dialog.SocialShareDialog
import com.newsapp.ui.vm.NewsDetailViewModel
import com.newsapp.util.hideKeyboard
import com.newsapp.util.likeCountFormat
import com.newsapp.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailActivity : BaseActivity() {

    companion object {
        private const val TAG = "NewsDetailActivity"
    }

    private var newsId: String? = ""
    private lateinit var shareLink: String
    private var likeStatus = "0"
    private var onlyUpdateComment = false
    private lateinit var binding: ActivityNewsDetailBinding
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsId = intent.getStringExtra(AppConstant.VIDEO_ID)
        if (newsId == null)
            newsId = ""

        binding.toolbar.titleName.text = "न्यूज़ डिटेल्स"

        binding.toolbar.ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }

        binding.ivShare.setOnClickListener {
            val socialShareDialog = SocialShareDialog(this@NewsDetailActivity, shareLink)
            socialShareDialog.show()
        }

        response()
        setData()

        binding.likeLayout.setOnClickListener {
            changeLikeStatus("1")
        }

        binding.dislikeLayout.setOnClickListener {
            changeLikeStatus("-1")
        }

        binding.ivSend.setOnClickListener {
            onlyUpdateComment = true
            if (!binding.tvComment.text.isNullOrEmpty()) {
                viewModel.savePostComment(
                    CommentRequest(
                        auth_token = Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                        comment = binding.tvComment.text.toString().trim(),
                        post_id = newsId!!
                    )
                )
            }
        }

    }

    private fun response() {
        viewModel.savePostCommentResponse.observe(this, {
            if (it.status) {
                binding.tvComment.setText("")
                hideKeyboard(binding.root)
                setData()
            } else
                toast(it.message)
        })

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

        viewModel.getPostsDetailResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data != null) {
                        if (!onlyUpdateComment) {
                            Glide.with(this)
                                .load("http://dbpnews.knovatik.com/" + it.data.image_big)
                                .into(binding.newsImage)
                            binding.tvHeading.text = it.data.title
                            binding.tvDetails.text = HtmlCompat.fromHtml(
                                it.data.content,
                                HtmlCompat.FROM_HTML_MODE_LEGACY
                            )
                            changeStateOfLikeNDislike(
                                it.data.like_status,
                                it.data.like_count,
                                it.data.dislike_count
                            )
                        }
                        if (it.data.comment_list.isNotEmpty()) {
                            binding.groupComment.visibility = View.VISIBLE
                            binding.commentRecycler.adapter =
                                CommentAdapter(this@NewsDetailActivity, it.data.comment_list)
                        } else {
                            binding.groupComment.visibility = View.GONE
                        }
                        shareLink = it.data.share_link
                    }
                } else {
                    toast(it.message)
                }
            } catch (e: Exception) {
            }
        })

    }

    private fun changeLikeStatus(status: String) {
        likeStatus = if (likeStatus == status)
            "0"
        else
            status
        Log.d(TAG, "changeLikeStatus: $likeStatus")

        viewModel.savePostLikeStatus(
            LikeRequest(
                Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                post_id = newsId!!,
                status = likeStatus
            )
        )
    }

    private fun setData() {
        onlyUpdateComment = false
        viewModel.getPostsDetail(
            PostDetailRequest(
                newsId!!,
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