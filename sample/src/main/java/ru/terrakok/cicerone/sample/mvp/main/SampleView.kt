package ru.terrakok.cicerone.sample.mvp.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface SampleView : MvpView {
    fun setTitle(title: String)
}