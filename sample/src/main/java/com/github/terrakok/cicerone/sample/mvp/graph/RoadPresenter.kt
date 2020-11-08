package com.github.terrakok.cicerone.sample.mvp.graph

import com.github.terrakok.cicerone.graph.GraphRouter
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.MvpView

@InjectViewState
class RoadPresenter(
    private val graphRouter: GraphRouter
): MvpPresenter<MvpView>() {

    fun onButtonClick() {
        graphRouter.currentVertex.edges.firstOrNull()?.id?.let { id ->
            graphRouter.navigateTo(id)
        }
    }

    fun onBackPressed() {
        graphRouter.exit()
    }
}