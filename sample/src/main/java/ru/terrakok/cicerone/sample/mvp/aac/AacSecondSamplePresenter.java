package ru.terrakok.cicerone.sample.mvp.aac;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.AacScreens;

public class AacSecondSamplePresenter extends MvpPresenter<MvpView> {

    private final Router router;

    public AacSecondSamplePresenter(Router router) {
        this.router = router;
    }

    public void onAacBlankPressed() {
        router.navigateTo(new AacScreens.AacBlankSampleScreen());
    }
}
