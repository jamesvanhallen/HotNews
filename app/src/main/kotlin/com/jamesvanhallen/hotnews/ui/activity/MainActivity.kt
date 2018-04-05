package com.jamesvanhallen.hotnews.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.jamesvanhallen.hotnews.R
import com.jamesvanhallen.hotnews.addFragment
import com.jamesvanhallen.hotnews.data.model.Article
import com.jamesvanhallen.hotnews.data.model.Source
import com.jamesvanhallen.hotnews.replaceFragment
import com.jamesvanhallen.hotnews.ui.fragment.ArticleDetailsFragment
import com.jamesvanhallen.hotnews.ui.fragment.ArticlesListFragment
import com.jamesvanhallen.hotnews.ui.fragment.SourceFragment
import com.jamesvanhallen.hotnews.ui.listener.OnScreenChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnScreenChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener { onBackPressed() }

        if (savedInstanceState == null) {
            replaceFragment(SourceFragment.newInstance())
        }
    }

    override fun onSourceSelected(source: Source) {
        val fragment = ArticlesListFragment.newInstance(source.id, source.sort)
        addFragment(fragment)
    }

    override fun onArticleSelected(article: Article) {
        val fragment = ArticleDetailsFragment.newInstance(article.url)
        addFragment(fragment)
    }

    override fun setScreenActionBar(showHomeButton: Boolean, title: String?) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(showHomeButton)
            setHomeButtonEnabled(showHomeButton)
        }
        if (!TextUtils.isEmpty(title)) toolbar.title = title
    }
}