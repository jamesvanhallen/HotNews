package com.jamesvanhallen.hotnews.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.jamesvanhallen.hotnews.data.model.Source
import com.jamesvanhallen.hotnews.repository.NewsRepository
import com.jamesvanhallen.hotnews.utils.DefaultExceptionHandler
import com.jamesvanhallen.hotnews.view.SourceView
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

@InjectViewState
class SourcePresenter(private val repository: NewsRepository) : MvpPresenter<SourceView>() {

    private var job: Job? = null

    fun onRefresh(showProgress: Boolean) {
        job?.cancel()

        val call = repository.fetchSourceResponse()
        job = launch(UI + DefaultExceptionHandler()) {
            try {
                if (showProgress) viewState.showProgress()
                val response = repository.enqueueCall(call)
                handleSuccess(response.sources)
            } catch (t: Throwable) {
                handleError(t)
            } finally {
                call.cancel()
                viewState.dismissProgress()
            }
        }
    }

    private fun handleSuccess(sources: List<Source>) {
        if (sources.isNotEmpty()) {
            viewState.showSources(sources)
        } else {
            viewState.showEmptyData()
        }
    }

    private fun handleError(error: Throwable) {
        viewState.showError(error)
    }
}