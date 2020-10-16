package ru.terrakok.cicerone.sample.mvp.animation

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 22.09.18.
 */
class PhotoSelection(private var selectedPhoto: Int) {

    private var listener: Listener? = null

    fun getSelectedPhoto(): Int {
        return selectedPhoto
    }

    fun setSelectedPhoto(selectedPhoto: Int) {
        this.selectedPhoto = selectedPhoto
        listener?.onChange(selectedPhoto)
    }

    fun setListener(listener: Listener?) {
        this.listener = listener
    }

    interface Listener {
        fun onChange(selectedPhoto: Int)
    }
}