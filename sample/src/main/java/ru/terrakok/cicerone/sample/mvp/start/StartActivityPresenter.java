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
        router.navigateTo(new Screens.MainScreen());
    }

    public void onMultiPressed() {
        router.navigateTo(new Screens.BottomNavigationScreen());
    }

    public void onResultWithAnimationPressed() {
        router.navigateTo(new Screens.ProfileScreen());
    }

    public void onBackPressed() {
        router.exit();
    }
}
