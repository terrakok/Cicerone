package ru.terrakok.cicerone.sample.mvp.aac;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.AacScreens;

public class AacBlankPresenter extends MvpPresenter<MvpView> {
    private final Router router;

    public AacBlankPresenter(Router router) {
        this.router = router;
    }

    public void onAacFirstPressed() {
        router.navigateTo(new AacScreens.AacFirstSampleScreen());
    }
}
