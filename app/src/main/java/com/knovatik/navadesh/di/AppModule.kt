package com.knovatik.navadesh.di

import android.content.Context
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.interfaces.Api
import com.knovatik.navadesh.network.retrofit.NetworkInterceptors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(api: Api): Repository {
        return Repository(api)
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Api {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://dbpnews.knovatik.com/Api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        networkInterceptors: NetworkInterceptors,
        headerInterceptor: Api.CustomInterceptor,
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptors)
            .addInterceptor(headerInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptors(@ApplicationContext context: Context): NetworkInterceptors {
        return NetworkInterceptors(context = context)
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Singleton
    @Provides
    fun provideCustomInterceptor(): Api.CustomInterceptor {
        return Api.CustomInterceptor()
    }

}