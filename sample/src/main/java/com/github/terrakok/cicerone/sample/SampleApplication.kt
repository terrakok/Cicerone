package com.github.terrakok.cicerone.sample

import android.app.Application
import com.github.terrakok.cicerone.sample.dagger.AppComponent
import com.github.terrakok.cicerone.sample.dagger.DaggerAppComponent

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
class SampleApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: SampleApplication
    }
}