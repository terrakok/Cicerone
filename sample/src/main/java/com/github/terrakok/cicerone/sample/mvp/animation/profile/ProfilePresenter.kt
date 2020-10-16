package com.github.terrakok.cicerone.sample.mvp.animation.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.Screens.selectPhotoScreen
import com.github.terrakok.cicerone.sample.mvp.animation.PhotoSelection

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
@InjectViewState
class ProfilePresenter(
        private val photoSelection: PhotoSelection,
        private val router: Router
) : MvpPresenter<ProfileView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updatePhoto()
    }

    override fun onDestroy() {
        photoSelection.setListener(null)
        super.onDestroy()
    }

    private fun updatePhoto() {
        viewState!!.showPhoto(photoSelection.getSelectedPhoto())
    }

    fun onPhotoClicked() {
        router.navigateTo(selectPhotoScreen())
    }

    fun onBackPressed() {
        router.exit()
    }

    init {
        photoSelection.setListener(object : PhotoSelection.Listener {
            override fun onChange(selectedPhoto: Int) {
                updatePhoto()
            }
        })
    }
}