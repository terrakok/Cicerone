package ru.terrakok.cicerone.sample.mvp.aac;

import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.Screens;

public class AacSecondSamplePresenter extends MvpPresenter<AacSecondSampleView> {

    private final Router router;

    public AacSecondSamplePresenter(Router router) {
        this.router = router;
    }

    public void onAacBlankPressed() {
        router.navigateTo(new Screens.AacBlankSampleScreen());
    }
}
