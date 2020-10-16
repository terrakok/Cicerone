package ru.terrakok.cicerone.sample.mvp.start;

import com.arellomobile.mvp.MvpPresenter;
import com.github.terrakok.cicerone.Router;

import ru.terrakok.cicerone.sample.Screens;

/**
 * Created by terrakok 21.11.16
 */
public class StartActivityPresenter extends MvpPresenter<StartActivityView> {
    private Router router;

    public StartActivityPresenter(Router router) {
        this.router = router;
    }

    public void onOrdinaryPressed() {
        router.navigateTo(Screens.mainScreen(), true);
    }

    public void onMultiPressed() {
        router.navigateTo(Screens.bottomNavigationScreen(), true);
    }

    public void onResultWithAnimationPressed() {
        router.navigateTo(Screens.profileScreen(), true);
    }

    public void onBackPressed() {
        router.exit();
    }
}
