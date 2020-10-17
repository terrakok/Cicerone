package com.github.terrakok.cicerone.sample.mvp.bottom.forward

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by terrakok 26.11.16
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ForwardView : MvpView {
    fun setChainText(chainText: String)
}