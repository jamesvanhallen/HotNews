package com.jamesvanhallen.hotnews.repository

import com.jamesvanhallen.hotnews.App
import com.jamesvanhallen.hotnews.ResponseException
import com.jamesvanhallen.hotnews.UnknownFailureException
import com.jamesvanhallen.hotnews.api.Api
import com.jamesvanhallen.hotnews.data.response.ArticlesListResponse
import com.jamesvanhallen.hotnews.data.response.SourceResponse
import kotlinx.coroutines.experimental.CancellableContinuation
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository {

    @Inject
    lateinit var api: Api

    init {
        App.appComponent.inject(this)
    }

    fun fetchSourceResponse(): Call<SourceResponse> = api.getSources()

    fun fetchArticlesResponse(source: String,
                              apiKey: String,
                              sortBy: String):
            Call<ArticlesListResponse> = api.getArticlesList(source, apiKey, sortBy)

    suspend fun <T> enqueueCall(call: Call<T>): T {
        return suspendCancellableCoroutine { cont: CancellableContinuation<T> ->
            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>?, t: Throwable?) {
                    if (call != null && call.isCanceled) {
                        return
                    }

                    cont.resumeWithException(t ?: UnknownFailureException())
                }

                override fun onResponse(call: Call<T>?, response: Response<T>?) {
                    if (response?.errorBody() != null) {
                        cont.resumeWithException(ResponseException(response.message(), response.errorBody()))
                    } else {
                        val body = response?.body()
                        if (body == null) cont.resumeWithException(NullPointerException()) else cont.resume(body)
                    }
                }
            })
            cont.invokeOnCompletion { call.cancel() }
        }
    }
}