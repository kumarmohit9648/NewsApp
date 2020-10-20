package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.comment.CommentRequest
import com.knovatik.navadesh.model.comment.CommentResponse
import com.knovatik.navadesh.model.like.LikeRequest
import com.knovatik.navadesh.model.posts.PostDetail
import com.knovatik.navadesh.model.posts.PostDetailRequest
import com.knovatik.navadesh.model.posts.PostStatus
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

class NewsDetailViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getPostsDetailResponse = MutableLiveData<PostDetail>()
    val getPostsDetailResponse get() = _getPostsDetailResponse
    fun getPostsDetail(postDetailRequest: PostDetailRequest) {
        Coroutines.main {
            _getPostsDetailResponse.postValue(repository.getPostsDetail(postDetailRequest))
        }
    }

    private var _savePostLikeStatusResponse = MutableLiveData<PostStatus>()
    val savePostLikeStatusResponse get() = _savePostLikeStatusResponse
    fun savePostLikeStatus(likeRequest: LikeRequest) {
        Coroutines.main {
            _savePostLikeStatusResponse.postValue(repository.savePostLikeStatus(likeRequest))
        }
    }

    private var _savePostCommentResponse = MutableLiveData<CommentResponse>()
    val savePostCommentResponse get() = _savePostCommentResponse
    fun savePostComment(commentRequest: CommentRequest) {
        Coroutines.main {
            _savePostCommentResponse.postValue(repository.savePostComment(commentRequest))
        }
    }

}