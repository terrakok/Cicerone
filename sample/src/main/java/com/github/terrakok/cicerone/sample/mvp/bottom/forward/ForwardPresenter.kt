package com.github.terrakok.cicerone.sample.mvp.bottom.forward

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.Screens.forwardScreen
import com.github.terrakok.cicerone.sample.Screens.githubScreen
import moxy.InjectViewState
import moxy.MvpPresenter

/**
 * Created by terrakok 26.11.16
 */
@InjectViewState
class ForwardPresenter(
        private val container: String,
        private val router: Router,
        private val number: Int
) : MvpPresenter<ForwardView>() {

    private fun createChain(number: Int): String {
        var chain = "[0]"
        for (i in 0 until number) {
            chain += "➔" + (i + 1)
        }
        return chain
    }

    fun onForwardPressed() {
        router.navigateTo(forwardScreen(container, number + 1))
    }

    fun onGithubPressed() {
        router.navigateTo(githubScreen())
    }

    fun onBackPressed() {
        router.exit()
    }

    init {
        viewState?.setChainText(createChain(number))
    }
}