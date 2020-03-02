package ru.terrakok.cicerone.sample.mvp.animation.photos;


import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.mvp.animation.PhotoSelection;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

@InjectViewState
public class SelectPhotoPresenter extends MvpPresenter<SelectPhotoView> {
    private Router router;
    private PhotoSelection photoSelection;

    public SelectPhotoPresenter(PhotoSelection photoSelection, Router router) {
        this.photoSelection = photoSelection;
        this.router = router;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showPhotos(new int[] {
                R.drawable.ava_1,
                R.drawable.ava_2,
                R.drawable.ava_3,
                R.drawable.ava_4
        });
    }

    public void onPhotoClick(int photoRes) {
        photoSelection.setSelectedPhoto(photoRes);
        router.exit();
    }

    public void onBackPressed() {
        router.exit();
    }
}
