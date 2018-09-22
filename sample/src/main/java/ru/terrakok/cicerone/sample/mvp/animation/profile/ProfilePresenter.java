package ru.terrakok.cicerone.sample.mvp.animation.profile;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.mvp.animation.PhotoSelection;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {

    private Router router;
    private PhotoSelection photoSelection;

    public ProfilePresenter(PhotoSelection photoSelection, Router router) {
        this.photoSelection = photoSelection;
        this.router = router;

        photoSelection.setListener(new PhotoSelection.Listener() {
            @Override
            public void onChange(int selectedPhoto) {
                updatePhoto();
            }
        });
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updatePhoto();
    }

    @Override
    public void onDestroy() {
        photoSelection.setListener(null);
        super.onDestroy();
    }

    private void updatePhoto() {
        getViewState().showPhoto(photoSelection.getSelectedPhoto());
    }

    public void onPhotoClicked() {
        router.navigateTo(new Screens.SelectPhotoScreen());
    }

    public void onBackPressed() {
        router.exit();
    }
}
