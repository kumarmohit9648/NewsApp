package com.newsapp.ui.activity

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import com.newsapp.databinding.ActivitySearchBinding
import com.newsapp.model.posts.Data
import com.newsapp.model.search.SearchRequest
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.NewsFeedAdapter
import com.newsapp.ui.vm.SearchViewModel
import com.newsapp.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search.*

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private val list = mutableListOf<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard(binding.searchBar)
                    viewModel.searchNews(
                        SearchRequest(
                            binding.searchBar.text.toString().trim()
                        )
                    )
                }
            }
            false
        }

        val adapter = NewsFeedAdapter(this, list!!)
        binding.recyclerNewsFeed.adapter = adapter

        viewModel.searchNewsResponse.observe(this, {
            try {
                if (it.status) {
                    if (it.data != null) {
                        if (it.data.isNotEmpty()) {
                            notFound.visibility = View.GONE
                            binding.recyclerNewsFeed.visibility = View.VISIBLE
                            adapter.setList(it.data)
                            adapter.notifyDataSetChanged()
                        } else {
                            notFound.visibility = View.VISIBLE
                            binding.recyclerNewsFeed.visibility = View.GONE
                        }
                    } else {
                        notFound.visibility = View.VISIBLE
                        binding.recyclerNewsFeed.visibility = View.GONE
                    }
                }
            } catch (e: Exception) {
            }
        })

    }
}