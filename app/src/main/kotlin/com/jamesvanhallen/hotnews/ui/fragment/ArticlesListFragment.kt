package com.jamesvanhallen.hotnews.ui.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jamesvanhallen.hotnews.*
import com.jamesvanhallen.hotnews.data.model.Article
import com.jamesvanhallen.hotnews.presenter.ArticlesListPresenter
import com.jamesvanhallen.hotnews.repository.NewsRepository
import com.jamesvanhallen.hotnews.ui.adapter.ArticleAdapter
import com.jamesvanhallen.hotnews.ui.listener.OnScreenChangeListener
import com.jamesvanhallen.hotnews.utils.httpErrorHandler
import com.jamesvanhallen.hotnews.view.ArticlesView
import kotlinx.android.synthetic.main.fragment_source.*


class ArticlesListFragment : MvpFragment(), ArticlesView {

    lateinit var adapter: ArticleAdapter

    private var source: String? = null

    private var sortArray: ArrayList<String>? = null

    private var sort: String? = null

    private var onScreenChangeListener: OnScreenChangeListener? = null

    @InjectPresenter
    lateinit var presenter: ArticlesListPresenter

    @ProvidePresenter
    fun providePresenter(): ArticlesListPresenter {
        return ArticlesListPresenter(NewsRepository())
    }

    companion object {
        fun newInstance(source: String, sortList: ArrayList<String>): Fragment {
            val articleFragment = ArticlesListFragment()
            val bundle = Bundle()
            bundle.putString(SOURCE, source)
            bundle.putStringArrayList(SORT_LIST, sortList)
            articleFragment.arguments = bundle
            return articleFragment
        }
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        onScreenChangeListener = activity as OnScreenChangeListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        source = arguments.getString(SOURCE)
        sortArray = arguments.getStringArrayList(SORT_LIST)
        sort = sortArray!![0]

        setHasOptionsMenu(true)
        adapter = ArticleAdapter { article -> openArticleDetails(article) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup, savedInstanceState: Bundle?): View? {
        return container.inflate(R.layout.fragment_article_list)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId < sortArray!!.size) {
            sort = sortArray!![item.itemId]
            getArticles(source!!, sort!!, true)
            return false
        }
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        for (item in sortArray!!) {
            menu.add(0, sortArray!!.indexOf(item), Menu.NONE, item)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        getArticles(source!!, sort!!, true)

        recyclerView.layoutManager = LinearLayoutManager(activity!!)
        recyclerView.adapter = adapter

        onScreenChangeListener?.setScreenActionBar(true)

        refresh.setOnRefreshListener { getArticles(source!!, sort!!, false) }
    }

    override fun openArticleDetails(article: Article) {
        onScreenChangeListener?.onArticleSelected(article)
    }

    private fun getArticles(source: String, sort: String, showProgress: Boolean) {
        presenter.getArticlesList(source, sort, showProgress)
    }


    override fun showArticles(articles: List<Article>) {
        tvEmpty.hide()
        recyclerView.display()
        adapter.setItems(articles)
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