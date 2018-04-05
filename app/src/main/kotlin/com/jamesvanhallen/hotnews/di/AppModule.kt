package com.jamesvanhallen.hotnews.di

import com.jamesvanhallen.hotnews.App
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun getApp(): App {
        return app
    }

    @Provides
    @Singleton
    fun providePicasso(app: App): Picasso {
        return Picasso.with(app)
    }
}