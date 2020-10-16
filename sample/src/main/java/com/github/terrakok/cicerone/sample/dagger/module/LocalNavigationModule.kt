package com.github.terrakok.cicerone.sample.dagger.module

import com.github.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by terrakok 24.11.16
 */
@Module
object LocalNavigationModule {

    @Provides
    @Singleton
    fun provideLocalNavigationHolder(): LocalCiceroneHolder = LocalCiceroneHolder()
}