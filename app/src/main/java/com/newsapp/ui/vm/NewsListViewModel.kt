package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.posts.Posts
import com.newsapp.model.posts.PostsRequest
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class NewsListViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getPostsResponse = MutableLiveData<Posts>()
    val getPostsResponse get() = _getPostsResponse
    fun getPosts(postsRequest: PostsRequest) {
        Coroutines.main {
            _getPostsResponse.postValue(repository.getPosts(postsRequest))
        }
    }

}