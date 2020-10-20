package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.posts.Posts
import com.knovatik.navadesh.model.posts.PostsRequest
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

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