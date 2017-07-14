package ru.terrakok.cicerone.sample.mvp.animation.photos;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

@InjectViewState
public class SelectPhotoPresenter extends MvpPresenter<SelectPhotoView> {
    private Router router;
    private final int RESULT_CODE;

    public SelectPhotoPresenter(Router router, int resultCode) {
        this.router = router;
        RESULT_CODE = resultCode;
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
        router.exitWithResult(RESULT_CODE, photoRes);
    }

    public void onBackPressed() {
        router.exit();
    }
}
