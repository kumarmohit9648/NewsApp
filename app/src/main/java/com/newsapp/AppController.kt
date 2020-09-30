package com.newsapp

import android.app.Application
import com.newsapp.network.Repository
import com.newsapp.network.interfaces.Api
import com.newsapp.network.retrofit.NetworkInterceptors
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class AppController : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppController))
        bind() from singleton { NetworkInterceptors(instance()) }
        bind() from singleton { Repository(instance()) }
        bind() from singleton { Api(instance()) }
    }
}