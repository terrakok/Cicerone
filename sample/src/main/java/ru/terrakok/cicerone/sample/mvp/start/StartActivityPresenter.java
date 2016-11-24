package ru.terrakok.cicerone.sample.mvp.start;

import com.arellomobile.mvp.MvpPresenter;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.Screens;

/**
 * Created by terrakok 21.11.16
 */
public class StartActivityPresenter extends MvpPresenter<StartActivityView> {
    private Router router;

    public StartActivityPresenter(Router router) {
        this.router = router;
    }

    public void onNextPressed() {
        router.replaceScreen(Screens.MAIN_ACTIVITY_SCREEN);
    }

    public void onBackPressed() {
        router.exit();
    }
}
