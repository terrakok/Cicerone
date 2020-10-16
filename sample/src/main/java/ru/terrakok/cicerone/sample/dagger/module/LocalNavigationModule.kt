package ru.terrakok.cicerone.sample.dagger.module

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder
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