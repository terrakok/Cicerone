package com.github.terrakok.cicerone.sample.mvp.animation.profile

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ProfileView : MvpView {
    fun showPhoto(resId: Int)
}