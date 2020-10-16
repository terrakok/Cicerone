package ru.terrakok.cicerone.sample.mvp.bottom.forward

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.terrakok.cicerone.Router
import ru.terrakok.cicerone.sample.Screens.forwardScreen
import ru.terrakok.cicerone.sample.Screens.githubScreen

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
        router.navigateTo(forwardScreen(container, number + 1), true)
    }

    fun onGithubPressed() {
        router.navigateTo(githubScreen(), true)
    }

    fun onBackPressed() {
        router.exit()
    }

    init {
        viewState?.setChainText(createChain(number))
    }
}