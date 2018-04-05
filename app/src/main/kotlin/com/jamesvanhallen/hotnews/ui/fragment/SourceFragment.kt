package com.jamesvanhallen.hotnews.ui.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jamesvanhallen.hotnews.*
import com.jamesvanhallen.hotnews.data.model.Source
import com.jamesvanhallen.hotnews.presenter.SourcePresenter
import com.jamesvanhallen.hotnews.repository.NewsRepository
import com.jamesvanhallen.hotnews.ui.adapter.SourceAdapter
import com.jamesvanhallen.hotnews.ui.listener.OnScreenChangeListener
import com.jamesvanhallen.hotnews.utils.httpErrorHandler
import com.jamesvanhallen.hotnews.view.SourceView
import kotlinx.android.synthetic.main.fragment_source.*

class SourceFragment : MvpFragment(), SourceView {

    private lateinit var adapter: SourceAdapter

    private var onScreenChangeListener: OnScreenChangeListener? = null

    @InjectPresenter
    lateinit var presenter: SourcePresenter

    @ProvidePresenter
    fun providePresenter(): SourcePresenter {
        return SourcePresenter(NewsRepository())
    }

    companion object {
        fun newInstance(): Fragment = SourceFragment()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onScreenChangeListener = activity as OnScreenChangeListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SourceAdapter { source -> openArticlesScreen(source) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View? =
            container.inflate(R.layout.fragment_source)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        loadData(true)

        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter

        onScreenChangeListener?.setScreenActionBar(false)

        refresh.setOnRefreshListener { loadData(false) }

    }

    private fun loadData(showProgress: Boolean) {
        if (activity.isOnline()) {
            presenter.onRefresh(showProgress)
        } else {
            activity.showSnackBar(recyclerView) { loadData(showProgress) }
        }
    }

    override fun openArticlesScreen(source: Source) {
        onScreenChangeListener?.onSourceSelected(source)
    }

    override fun showSources(sources: List<Source>) {
        tvEmpty.hide()
        recyclerView.display()
        adapter.setItems(sources)
    }

    override fun showError(error: Throwable) {
        showEmptyView(httpErrorHandler(activity, error))
    }

    override fun showEmptyData() {
        showEmptyView(getString(R.string.empty_data))
    }

    private fun showEmptyView(errorText: String) {
        tvEmpty.display()
        tvEmpty.text = errorText
        recyclerView.hide()
    }

    override fun showProgress() {
        refresh.isRefreshing = true
    }

    override fun dismissProgress() {
        refresh.isRefreshing = false
    }
}