package com.jamesvanhallen.hotnews.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jamesvanhallen.hotnews.strategy.AddToEndSingleByTagStrategy
import com.jamesvanhallen.hotnews.strategy.OneExecutionByTagStrategy


interface BaseNetworkView : MvpView {

    @StateStrategyType(value = AddToEndSingleByTagStrategy::class, tag = "showProgress")
    fun showProgress()

    @StateStrategyType(value = OneExecutionByTagStrategy::class, tag = "showProgress")
    fun dismissProgress()

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showError(error: Throwable)

    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showEmptyData()

}