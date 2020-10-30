package com.github.terrakok.cicerone.sample.mvp.main

import android.os.Handler
import android.os.Looper
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.Screens.Sample
import moxy.InjectViewState
import moxy.MvpPresenter
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
@InjectViewState
class SamplePresenter(
        private val router: Router,
        private val screenNumber: Int
) : MvpPresenter<SampleView>() {

    private val executorService = Executors.newSingleThreadScheduledExecutor()

    private var future: ScheduledFuture<*>? = null

    fun onBackCommandClick() {
        router.exit()
    }

    fun onForwardCommandClick() {
        router.navigateTo(Sample(screenNumber + 1))
    }

    fun onReplaceCommandClick() {
        router.replaceScreen(Sample(screenNumber + 1))
    }

    fun onNewChainCommandClick() {
        router.newChain(
                Sample(screenNumber + 1),
                Sample(screenNumber + 2),
                Sample(screenNumber + 3)
        )
    }

    fun onNewRootChainCommandClick() {
        router.newRootChain(
                Sample(screenNumber + 1),
                Sample(screenNumber + 2),
                Sample(screenNumber + 3)
        )
    }

    fun onFinishChainCommandClick() {
        router.finishChain()
    }

    fun onNewRootCommandClick() {
        router.newRootScreen(Sample(screenNumber + 1))
    }

    fun onForwardWithDelayCommandClick() {
        future?.cancel(true)
        future = executorService.schedule({ //WARNING! Navigation must be only in UI thread.
            Handler(Looper.getMainLooper()).post {
                router.navigateTo(Sample(screenNumber + 1))
            }
        }, 5, TimeUnit.SECONDS)
    }

    fun onBackToCommandClick() {
        router.backTo(Sample(3))
    }

    init {
        viewState?.setTitle("Screen $screenNumber")
    }
}