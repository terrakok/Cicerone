package com.github.terrakok.cicerone.sample.mvp.start

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.Screens.bottomNavigationScreen
import com.github.terrakok.cicerone.sample.Screens.mainScreen
import com.github.terrakok.cicerone.sample.Screens.profileScreen
import moxy.MvpPresenter

/**
 * Created by terrakok 21.11.16
 */
class StartActivityPresenter(private val router: Router) : MvpPresenter<StartActivityView>() {

    fun onOrdinaryPressed() {
        router.navigateTo(mainScreen())
    }

    fun onMultiPressed() {
        router.navigateTo(bottomNavigationScreen())
    }

    fun onResultWithAnimationPressed() {
        router.navigateTo(profileScreen())
    }

    fun onBackPressed() {
        router.exit()
    }

}