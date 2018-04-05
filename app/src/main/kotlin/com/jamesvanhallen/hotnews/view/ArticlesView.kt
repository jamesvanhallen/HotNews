package com.jamesvanhallen.hotnews.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jamesvanhallen.hotnews.data.model.Article

interface ArticlesView : BaseNetworkView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showArticles(articles: List<Article>)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun openArticleDetails(article: Article)
}