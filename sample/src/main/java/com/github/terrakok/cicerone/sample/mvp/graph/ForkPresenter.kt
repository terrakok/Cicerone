package com.github.terrakok.cicerone.sample.mvp.graph

import com.github.terrakok.cicerone.graph.GraphRouter
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView

@InjectViewState
class ForkPresenter(
    private val graphRouter: GraphRouter
): MvpPresenter<MvpView>() {

    fun onTopButtonClick() {
        graphRouter.currentVertex.edges.firstOrNull()?.id?.let { id ->
            graphRouter.navigateTo(id)
        }
    }

    fun onBottomButtonClick() {
        graphRouter.currentVertex.edges.lastOrNull()?.id?.let { id ->
            graphRouter.navigateTo(id)
        }
    }

    fun onBackPressed() {
        graphRouter.exit()
    }
}