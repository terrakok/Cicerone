package com.github.terrakok.cicerone.sample.dagger.module

import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.mvp.animation.PhotoSelection
import dagger.Module
import dagger.Provides
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