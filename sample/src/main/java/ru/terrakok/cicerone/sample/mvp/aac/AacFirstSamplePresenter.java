package ru.terrakok.cicerone.sample.mvp.aac;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.AacScreens;

public class AacFirstSamplePresenter extends MvpPresenter<MvpView> {

    private final Router router;

    public AacFirstSamplePresenter(Router router) {
        this.router = router;
    }

    public void onAacSecondPressed() {
        router.navigateTo(new AacScreens.AacSecondSampleScreen());
    }
}
