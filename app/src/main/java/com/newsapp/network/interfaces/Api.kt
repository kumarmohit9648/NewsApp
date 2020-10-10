package com.newsapp.network.interfaces

import com.newsapp.model.menu.MenuCategories
import com.newsapp.model.posts.Posts
import com.newsapp.model.posts.PostsRequest
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

    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request().newBuilder()
                .build()
            return chain.proceed(request)
        }
    }

}