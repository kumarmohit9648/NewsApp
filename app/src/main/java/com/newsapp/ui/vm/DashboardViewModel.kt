package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.menu.MenuCategories
import com.newsapp.model.posts.Posts
import com.newsapp.model.posts.PostsRequest
import com.newsapp.model.submenu.SubMenuCategories
import com.newsapp.model.submenu.SubMenuRequest
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class DashboardViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getMenuCategoryResponse = MutableLiveData<MenuCategories>()
    val getMenuCategoryResponse get() = _getMenuCategoryResponse
    fun getMenuCategory() {
        Coroutines.main {
            getMenuCategoryResponse.postValue(repository.getMenuCategory())
        }
    }

    private var _getSubcategoryListResponse = MutableLiveData<SubMenuCategories>()
    val getSubcategoryListResponse get() = _getSubcategoryListResponse
    fun getSubcategoryList(subMenuRequest: SubMenuRequest) {
        Coroutines.main {
            getSubcategoryListResponse.postValue(repository.getSubcategoryList(subMenuRequest))
        }
    }

    private var _getPostsResponse = MutableLiveData<Posts>()
    val getPostsResponse get() = _getPostsResponse
    fun getPosts(postsRequest: PostsRequest) {
        Coroutines.main {
            getPostsResponse.postValue(repository.getPosts(postsRequest))
        }
    }

}