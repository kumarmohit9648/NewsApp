package com.newsapp.network.interfaces

import com.newsapp.model.AuthToken
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
import com.newsapp.model.submenu.SubMenuCategories
import com.newsapp.model.submenu.SubMenuRequest
import okhttp3.Interceptor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("get-category-list")
    suspend fun getMenuCategory(): Response<MenuCategories>

    @POST("get-subcategory_list")
    suspend fun getSubcategoryList(@Body subMenuRequest: SubMenuRequest): Response<SubMenuCategories>

    @POST("get-post")
    suspend fun getPosts(@Body subMenuRequest: PostsRequest): Response<Posts>

    @POST("get-post-detail")
    suspend fun getPostsDetail(@Body postDetailRequest: PostDetailRequest): Response<PostDetail>

    @POST("save-post-like-status")
    suspend fun savePostLikeStatus(@Body likeRequest: LikeRequest): Response<PostStatus>

    @POST("user-registration")
    suspend fun userRegistration(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("get-notification-count")
    suspend fun getNotificationCount(@Body authToken: AuthToken): Response<LikeResponse>

    @POST("save-post-comment")
    suspend fun savePostComment(@Body commentRequest: CommentRequest): Response<CommentResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<RegisterResponse>

    @POST("get-notification-list")
    suspend fun getNotificationList(@Body authToken: AuthToken): Response<Notification>

    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request().newBuilder()
                .build()
            return chain.proceed(request)
        }
    }

}