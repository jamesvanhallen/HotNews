package com.jamesvanhallen.hotnews.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jamesvanhallen.hotnews.BuildConfig
import com.jamesvanhallen.hotnews.api.Api
import com.jamesvanhallen.hotnews.utils.setTrustAllCertsSslSocketFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            setTrustAllCertsSslSocketFactory(client)
        }

        return client.build()

    }

    @Provides
    @Singleton
    fun provideApi(client: OkHttpClient, gson: Gson): Api {
        val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)

        return builder.build().create(Api::class.java)
    }
}
