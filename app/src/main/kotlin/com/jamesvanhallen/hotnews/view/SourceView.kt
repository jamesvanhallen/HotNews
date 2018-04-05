package com.jamesvanhallen.hotnews.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jamesvanhallen.hotnews.data.model.Source

interface SourceView : BaseNetworkView {

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showSources(sources: List<Source>)

    @StateStrategyType(value = OneExecutionStateStrategy::class)
    fun openArticlesScreen(source: Source)
}