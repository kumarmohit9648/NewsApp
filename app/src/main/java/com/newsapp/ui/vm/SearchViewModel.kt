package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.posts.Posts
import com.newsapp.model.search.SearchRequest
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class SearchViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _searchNewsResponse = MutableLiveData<Posts>()
    val searchNewsResponse get() = _searchNewsResponse
    fun searchNews(searchRequest: SearchRequest) {
        Coroutines.main {
            _searchNewsResponse.postValue(repository.searchNews(searchRequest))
        }
    }

}