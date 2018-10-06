package ru.terrakok.cicerone.sample.mvp.aac;

import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.Screens;

public class AacFirstSamplePresenter extends MvpPresenter<AacFirstSampleView> {

    private final Router router;

    public AacFirstSamplePresenter(Router router) {
        this.router = router;
    }

    public void onAacSecondPressed() {
        router.navigateTo(new Screens.AacSecondSampleScreen());
    }
}
