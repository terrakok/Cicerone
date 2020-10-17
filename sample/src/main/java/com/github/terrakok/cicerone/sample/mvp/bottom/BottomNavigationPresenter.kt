package com.github.terrakok.cicerone.sample.mvp.bottom

import com.github.terrakok.cicerone.Router
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by terrakok 25.11.16
 */
@InjectViewState
class BottomNavigationPresenter(
        private val router: Router
) : MvpPresenter<BottomNavigationView>() {

    fun onBackPressed() {
        router.exit()
    }
}