package com.newsapp.network

import com.newsapp.model.AuthToken
import com.newsapp.model.CommonResponse
import com.newsapp.model.comment.CommentRequest
import com.newsapp.model.comment.CommentResponse
import com.newsapp.model.like.LikeRequest
import com.newsapp.model.like.LikeResponse
import com.newsapp.model.login.LoginRequest
import com.newsapp.model.menu.MenuCategories
import com.newsapp.model.notification.Notification
import com.newsapp.model.posts.*
import com.newsapp.model.register.RegisterRequest
import com.newsapp.model.register.RegisterResponse
import com.newsapp.model.search.SearchRequest
import com.newsapp.model.section.SectionItem
import com.newsapp.model.section.SectionItemRequest
import com.newsapp.model.submenu.SubMenuCategories
import com.newsapp.model.submenu.SubMenuRequest
import com.newsapp.network.interfaces.Api
import com.newsapp.network.retrofit.SafeApiRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    suspend fun getSubcategoryList(request: SubMenuRequest): SubMenuCategories {
        var response = SubMenuCategories(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getSubcategoryList(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getPosts(request: PostsRequest): Posts {
        var response = Posts(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getPosts(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun searchNews(request: SearchRequest): Posts {
        var response = Posts(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.searchNews(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getPostsDetail(request: PostDetailRequest): PostDetail {
        var response = PostDetail(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getPostsDetail(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun savePostLikeStatus(request: LikeRequest): PostStatus {
        var response = PostStatus("Network Error", null, "", false, "")
        try {
            response = apiRequest { _api.savePostLikeStatus(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getNotificationCount(request: AuthToken): LikeResponse {
        var response = LikeResponse(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getNotificationCount(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getNotificationList(request: AuthToken): Notification {
        var response = Notification(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getNotificationList(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun userRegistration(request: RegisterRequest): RegisterResponse {
        var response = RegisterResponse(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.userRegistration(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun savePostComment(request: CommentRequest): CommentResponse {
        var response = CommentResponse("", false, "")
        try {
            response = apiRequest { _api.savePostComment(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun login(request: LoginRequest): RegisterResponse {
        var response = RegisterResponse(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.login(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getSectionItem(request: SectionItemRequest): SectionItem {
        var response = SectionItem(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getSectionItem(request) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun uploadContent(
        image_file: MultipartBody.Part,
        video_file: MultipartBody.Part,
        audio_file: MultipartBody.Part,
        auth_token: RequestBody,
        title: RequestBody,
        content: RequestBody,
        state: RequestBody,
        district: RequestBody,
        village: RequestBody,
        address: RequestBody
    ): CommonResponse {
        var response = CommonResponse(null, "Network Error", false, "")
        try {
            response = apiRequest {
                _api.uploadContent(
                    image_file,
                    video_file,
                    audio_file,
                    auth_token,
                    title,
                    content,
                    state,
                    district,
                    village,
                    address
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

}