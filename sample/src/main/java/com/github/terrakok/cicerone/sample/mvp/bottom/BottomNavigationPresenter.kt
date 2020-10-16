package com.github.terrakok.cicerone.sample.mvp.bottom

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.terrakok.cicerone.Router

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