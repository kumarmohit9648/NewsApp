package com.knovatik.navadesh.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityNewsListBinding
import com.knovatik.navadesh.model.posts.Data
import com.knovatik.navadesh.model.posts.PostsRequest
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.adapter.NewsFeedAdapter
import com.knovatik.navadesh.ui.vm.NewsListViewModel
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_list.*

@AndroidEntryPoint
class NewsListActivity : BaseActivity() {

    private lateinit var binding: ActivityNewsListBinding
    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentTitle = intent.getStringExtra(AppConstant.TITLE_KEY)
        binding.toolbar.titleName.text =
            if (intentTitle.isNullOrEmpty()) "न्यूज़ डिटेल्स" else intentTitle

        val categoryId =
            if (intent.getStringExtra(AppConstant.CATEGORY_ID) == null)
                ""
            else intent.getStringExtra(
                AppConstant.CATEGORY_ID
            )
        val subCategoryId = intent.getStringExtra(AppConstant.SUB_CATEGORY_ID)

        binding.toolbar.ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }

        setData(categoryId, subCategoryId)
    }

    private fun setData(categoryId: String?, subCategoryId: String?) {
        viewModel.getPostsResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data!!.isNotEmpty()) {
                        notFound.visibility = View.GONE
                        binding.recyclerNewsFeed.visibility = View.VISIBLE
                        binding.recyclerNewsFeed.adapter =
                            NewsFeedAdapter(this, it.data as MutableList<Data>)
                    } else {
                        notFound.visibility = View.VISIBLE
                        binding.recyclerNewsFeed.visibility = View.GONE
                    }
                } else {
                    notFound.visibility = View.VISIBLE
                    binding.recyclerNewsFeed.visibility = View.GONE
                }
            } catch (e: Exception) {
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        })
        try {
            viewModel.getPosts(
                PostsRequest(
                    categoryId!!,
                    subCategoryId!!,
                    Prefs.getString(AppConstant.AUTH_TOKEN, "")
                )
            )
            binding.progressBar.visibility = View.VISIBLE
        } catch (e: Exception) {
        }
    }

}