package ru.terrakok.cicerone.sample.dagger.module

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.sample.R
import ru.terrakok.cicerone.sample.mvp.animation.PhotoSelection
import javax.inject.Singleton

/**
 * Created by terrakok 24.11.16
 */
@Module
object PhotoSelectionModule {

    private val photoSelection = PhotoSelection(R.drawable.ava_1)

    @Provides
    @Singleton
    fun providePhotoSelection() = photoSelection
}