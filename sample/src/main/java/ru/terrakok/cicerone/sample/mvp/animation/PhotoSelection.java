package ru.terrakok.cicerone.sample.mvp.animation;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 22.09.18.
 */
public class PhotoSelection {
    private int selectedPhoto;
    private Listener listener;

    public PhotoSelection(int defaultPhoto) {
        this.selectedPhoto = defaultPhoto;
    }

    public int getSelectedPhoto() {
        return selectedPhoto;
    }

    public void setSelectedPhoto(int selectedPhoto) {
        this.selectedPhoto = selectedPhoto;
        if (listener != null) {
            listener.onChange(selectedPhoto);
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onChange(int selectedPhoto);
    }
}
