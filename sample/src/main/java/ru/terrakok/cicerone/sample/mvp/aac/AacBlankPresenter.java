package ru.terrakok.cicerone.sample.mvp.aac;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.Screens;

@InjectViewState
public class AacBlankPresenter extends MvpPresenter<AacBlankView> {
    private final Router router;

    public AacBlankPresenter(Router router) {
        this.router = router;
    }

    public void onAacFirstPressed() {
        router.navigateTo(new Screens.AacFirstSampleScreen());
    }
}
