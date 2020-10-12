package com.newsapp.network

import com.newsapp.model.AuthToken
import com.newsapp.model.like.LikeRequest
import com.newsapp.model.like.LikeResponse
import com.newsapp.model.menu.MenuCategories
import com.newsapp.model.posts.*
import com.newsapp.model.register.RegisterRequest
import com.newsapp.model.register.RegisterResponse
import com.newsapp.model.submenu.SubMenuCategories
import com.newsapp.model.submenu.SubMenuRequest
import com.newsapp.network.interfaces.Api
import com.newsapp.network.retrofit.SafeApiRequest
import javax.inject.Inject

class Repository @Inject constructor(private val _api: Api) : SafeApiRequest() {

    suspend fun getMenuCategory(): MenuCategories {
        var response = MenuCategories(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getMenuCategory() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getSubcategoryList(subMenuRequest: SubMenuRequest): SubMenuCategories {
        var response = SubMenuCategories(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getSubcategoryList(subMenuRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getPosts(postsRequest: PostsRequest): Posts {
        var response = Posts(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getPosts(postsRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getPostsDetail(postDetailRequest: PostDetailRequest): PostDetail {
        var response = PostDetail(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getPostsDetail(postDetailRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun savePostLikeStatus(likeRequest: LikeRequest): PostStatus {
        var response = PostStatus("Network Error", null, "", false, "")
        try {
            response = apiRequest { _api.savePostLikeStatus(likeRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getNotificationCount(authToken: AuthToken): LikeResponse {
        var response = LikeResponse(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getNotificationCount(authToken) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun userRegistration(registerRequest: RegisterRequest): RegisterResponse {
        var response = RegisterResponse(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.userRegistration(registerRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

}