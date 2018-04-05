package com.jamesvanhallen.hotnews.ui.listener

import com.jamesvanhallen.hotnews.data.model.Article
import com.jamesvanhallen.hotnews.data.model.Source

interface OnScreenChangeListener {

    fun onSourceSelected(source: Source)

    fun onArticleSelected(article: Article)

    fun setScreenActionBar(showHomeButton: Boolean, title: String? = null)

}