package com.jamesvanhallen.hotnews.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.jamesvanhallen.hotnews.BuildConfig
import com.jamesvanhallen.hotnews.data.model.Article
import com.jamesvanhallen.hotnews.repository.NewsRepository
import com.jamesvanhallen.hotnews.utils.DefaultExceptionHandler
import com.jamesvanhallen.hotnews.view.ArticlesView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

@InjectViewState
class ArticlesListPresenter(private val repository: NewsRepository) : MvpPresenter<ArticlesView>() {

    private var job: Job? = null

    fun getArticlesList(source: String,
                        sort: String,
                        showProgress: Boolean) = launch(UI) {
        job?.cancel()
        val call = repository.fetchArticlesResponse(source, BuildConfig.API_KEY, sort)
        job = launch(UI + DefaultExceptionHandler()) {
            try {
                if (showProgress) viewState.showProgress()
                val response = repository.enqueueCall(call)
                handleSuccess(response.articles)
            } catch (t: Throwable) {
                handleError(t)
            } finally {
                call.cancel()
                viewState.dismissProgress()
            }
        }
    }

    private fun handleSuccess(sources: List<Article>) {
        if (sources.isNotEmpty()) {
            viewState.showArticles(sources)
        } else {
            viewState.showEmptyData()
        }
    }

    private fun handleError(error: Throwable) {
        viewState.showError(error)
    }
}