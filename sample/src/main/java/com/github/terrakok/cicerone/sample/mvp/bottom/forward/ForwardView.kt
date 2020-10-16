package com.github.terrakok.cicerone.sample.mvp.bottom.forward

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by terrakok 26.11.16
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ForwardView : MvpView {
    fun setChainText(chainText: String)
}