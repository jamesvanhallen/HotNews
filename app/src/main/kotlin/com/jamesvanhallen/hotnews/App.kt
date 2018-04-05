package com.jamesvanhallen.hotnews

import android.app.Application
import com.jamesvanhallen.hotnews.di.AppComponent
import com.jamesvanhallen.hotnews.di.AppModule
import com.jamesvanhallen.hotnews.di.DaggerAppComponent
import com.jamesvanhallen.hotnews.di.NetworkModule

class App : Application() {

    companion object {

        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .build()
    }
}