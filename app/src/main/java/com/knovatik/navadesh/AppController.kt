package com.knovatik.navadesh

import android.app.Application
import android.content.ContextWrapper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Easy Preference
        initSharedPref()

        // AndroidNetworking.initialize(applicationContext);
        // AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
    }

    private fun initSharedPref() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

}