package ru.terrakok.cicerone.sample.mvp.bottom;


import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

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
