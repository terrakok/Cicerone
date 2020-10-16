package ru.terrakok.cicerone.sample.mvp.start

import com.arellomobile.mvp.MvpPresenter
import com.github.terrakok.cicerone.Router
import ru.terrakok.cicerone.sample.Screens.bottomNavigationScreen
import ru.terrakok.cicerone.sample.Screens.mainScreen
import ru.terrakok.cicerone.sample.Screens.profileScreen

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