package ru.terrakok.cicerone.sample.mvp.bottom;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.github.terrakok.cicerone.Router;

/**
 * Created by terrakok 25.11.16
 */
@InjectViewState
public class BottomNavigationPresenter extends MvpPresenter<BottomNavigationView> {
    private Router router;

    public BottomNavigationPresenter(Router router) {
        this.router = router;
    }

    public void onBackPressed() {
        router.exit();
    }
}
