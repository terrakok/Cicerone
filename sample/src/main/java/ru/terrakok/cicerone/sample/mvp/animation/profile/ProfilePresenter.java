package ru.terrakok.cicerone.sample.mvp.animation.profile;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.result.ResultListener;
import ru.terrakok.cicerone.sample.Screens;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

@InjectViewState
public class ProfilePresenter extends MvpPresenter<ProfileView> {
    private static final int PHOTO_RESULT_CODE = 42;

    private Router router;
    private int currentPhoto;

    public ProfilePresenter(int defaultPhoto, Router router) {
        currentPhoto = defaultPhoto;
        this.router = router;

        router.setResultListener(PHOTO_RESULT_CODE, new ResultListener() {
            @Override
            public void onResult(@NonNull Object resultData) {
                currentPhoto = (int) resultData;
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
        router.removeResultListener(PHOTO_RESULT_CODE);
        super.onDestroy();
    }

    private void updatePhoto() {
        getViewState().showPhoto(currentPhoto);
    }

    public void onPhotoClicked() {
        router.navigateTo(Screens.SELECT_PHOTO_SCREEN, PHOTO_RESULT_CODE);
    }

    public void onBackPressed() {
        router.exit();
    }
}
