package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.like.LikeRequest
import com.newsapp.model.like.LikeResponse
import com.newsapp.model.posts.PostDetail
import com.newsapp.model.posts.PostDetailRequest
import com.newsapp.model.posts.PostStatus
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class NewsDetailViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getPostsDetailResponse = MutableLiveData<PostDetail>()
    val getPostsDetailResponse get() = _getPostsDetailResponse
    fun getPostsDetail(postDetailRequest: PostDetailRequest) {
        Coroutines.main {
            getPostsDetailResponse.postValue(repository.getPostsDetail(postDetailRequest))
        }
    }

    private var _savePostLikeStatusResponse = MutableLiveData<PostStatus>()
    val savePostLikeStatusResponse get() = _savePostLikeStatusResponse
    fun savePostLikeStatus(likeRequest: LikeRequest) {
        Coroutines.main {
            savePostLikeStatusResponse.postValue(repository.savePostLikeStatus(likeRequest))
        }
    }

}