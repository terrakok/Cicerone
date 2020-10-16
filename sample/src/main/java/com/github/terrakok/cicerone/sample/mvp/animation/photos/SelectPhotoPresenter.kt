package com.github.terrakok.cicerone.sample.mvp.animation.photos

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.mvp.animation.PhotoSelection

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
@InjectViewState
class SelectPhotoPresenter(
        private val photoSelection: PhotoSelection,
        private val router: Router
) : MvpPresenter<SelectPhotoView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState!!.showPhotos(intArrayOf(
                R.drawable.ava_1,
                R.drawable.ava_2,
                R.drawable.ava_3,
                R.drawable.ava_4
        ))
    }

    fun onPhotoClick(photoRes: Int) {
        photoSelection.setSelectedPhoto(photoRes)
        router.exit()
    }

    fun onBackPressed() {
        router.exit()
    }
}