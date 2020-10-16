package ru.terrakok.cicerone.sample.mvp.main

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.terrakok.cicerone.Router
import ru.terrakok.cicerone.sample.Screens.sampleScreen
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */
@InjectViewState
class SamplePresenter(private val router: Router, private val screenNumber: Int) : MvpPresenter<SampleView?>() {

    private val executorService = Executors.newSingleThreadScheduledExecutor()

    private var future: ScheduledFuture<*>? = null

    fun onBackCommandClick() {
        router.exit()
    }

    fun onForwardCommandClick() {
        router.navigateTo(sampleScreen(screenNumber + 1))
    }

    fun onReplaceCommandClick() {
        router.replaceScreen(sampleScreen(screenNumber + 1))
    }

    fun onNewChainCommandClick() {
        router.newChain(
                sampleScreen(screenNumber + 1),
                sampleScreen(screenNumber + 2),
                sampleScreen(screenNumber + 3)
        )
    }

    fun onFinishChainCommandClick() {
        router.finishChain()
    }

    fun onNewRootCommandClick() {
        router.newRootScreen(sampleScreen(screenNumber + 1))
    }

    fun onForwardWithDelayCommandClick() {
        future?.cancel(true)
        future = executorService.schedule({ //WARNING! Navigation must be only in UI thread.
            Handler(Looper.getMainLooper()).post {
                router.navigateTo(sampleScreen(screenNumber + 1))
            }
        }, 5, TimeUnit.SECONDS)
    }

    fun onBackToCommandClick() {
        router.backTo(sampleScreen(3))
    }

    init {
        viewState?.setTitle("Screen $screenNumber")
    }
}