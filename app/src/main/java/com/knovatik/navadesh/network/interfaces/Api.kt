package com.knovatik.navadesh.network.interfaces

import com.knovatik.navadesh.model.AuthToken
import com.knovatik.navadesh.model.CommonResponse
import com.knovatik.navadesh.model.comment.CommentRequest
import com.knovatik.navadesh.model.comment.CommentResponse
import com.knovatik.navadesh.model.like.LikeRequest
import com.knovatik.navadesh.model.like.LikeResponse
import com.knovatik.navadesh.model.login.LoginRequest
import com.knovatik.navadesh.model.menu.MenuCategories
import com.knovatik.navadesh.model.notification.Notification
import com.knovatik.navadesh.model.notification.NotificationStatus
import com.knovatik.navadesh.model.posts.*
import com.knovatik.navadesh.model.profile.ProfileDetail
import com.knovatik.navadesh.model.profile.UpdateProfile
import com.knovatik.navadesh.model.profile.UpdateProfileImage
import com.knovatik.navadesh.model.register.RegisterRequest
import com.knovatik.navadesh.model.register.RegisterResponse
import com.knovatik.navadesh.model.search.SearchRequest
import com.knovatik.navadesh.model.section.SectionItem
import com.knovatik.navadesh.model.section.SectionItemRequest
import com.knovatik.navadesh.model.social.SocialLoginRequest
import com.knovatik.navadesh.model.social.SocialProfileDetail
import com.knovatik.navadesh.model.submenu.SubMenuCategories
import com.knovatik.navadesh.model.submenu.SubMenuRequest
import com.knovatik.navadesh.model.weather.Weather
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("current.json?")
    fun weather(
        @Query("key") key: String,
        @Query("q") district: String
    ): Call<Weather>

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

    @POST("search-news")
    suspend fun searchNews(@Body searchRequest: SearchRequest): Response<Posts>

    @POST("get-section-item")
    suspend fun getSectionItem(@Body sectionItemRequest: SectionItemRequest): Response<SectionItem>

    @POST("get-profile")
    suspend fun getProfile(@Body request: AuthToken): Response<ProfileDetail>

    @POST("update-profile")
    suspend fun updateProfile(@Body request: UpdateProfile): Response<ProfileDetail>

    @POST("update-profile-image")
    suspend fun updateProfileImage(@Body request: UpdateProfileImage): Response<CommonResponse>

    @POST("update-notification-status")
    suspend fun updateNotificationStatus(@Body request: NotificationStatus): Response<CommonResponse>

    @POST("social-registration")
    suspend fun socialRegistration(@Body request: SocialLoginRequest): Response<SocialProfileDetail>

    @Multipart
    @POST("upload-content")
    suspend fun uploadContent(
        @Part image_file: MultipartBody.Part,
        @Part video_file: MultipartBody.Part,
        @Part audio_file: MultipartBody.Part,
        @Part("auth_token") auth_token: RequestBody,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("state") state: RequestBody,
        @Part("district") district: RequestBody,
        @Part("village") village: RequestBody,
        @Part("address") address: RequestBody,
    ): Response<CommonResponse>

    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request().newBuilder()
                .build()
            return chain.proceed(request)
        }
    }

}