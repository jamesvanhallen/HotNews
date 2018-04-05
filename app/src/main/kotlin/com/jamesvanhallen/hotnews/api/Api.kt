package com.jamesvanhallen.hotnews.api

import com.jamesvanhallen.hotnews.data.model.annotation.Sort
import com.jamesvanhallen.hotnews.data.response.ArticlesListResponse
import com.jamesvanhallen.hotnews.data.response.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("sources")
    fun getSources():
            Call<SourceResponse>

    @GET("articles")
    fun getArticlesList(@Query("source") source: String,
                        @Query("apiKey") apiKey: String,
                        @Sort @Query("sortBy") sortBy: String):
            Call<ArticlesListResponse>
}