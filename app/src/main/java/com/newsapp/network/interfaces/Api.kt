package com.newsapp.network.interfaces

import com.newsapp.model.menu.MenuCategories
import com.newsapp.network.retrofit.NetworkInterceptors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api {

    @GET("get-menu-category")
    suspend fun getMenuCategory(): Response<MenuCategories>

    companion object {
        operator fun invoke(
            networkInterceptors: NetworkInterceptors
        ): Api {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val headerInterceptor =
                CustomInterceptor()

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkInterceptors)
                .addInterceptor(headerInterceptor)
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://dbpnews.knovatik.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }
    }

    class CustomInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request().newBuilder()
                .build()
            return chain.proceed(request)
        }
    }

}