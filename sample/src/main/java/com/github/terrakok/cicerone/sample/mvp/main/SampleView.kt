package com.github.terrakok.cicerone.sample.mvp.main

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface SampleView : MvpView {
    fun setTitle(title: String)
}