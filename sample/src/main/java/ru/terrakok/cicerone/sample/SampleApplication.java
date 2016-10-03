package ru.terrakok.cicerone.sample;

import com.arellomobile.mvp.MvpApplication;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/**
 * Created by terrakok 01.10.16
 */
public class SampleApplication extends MvpApplication {
    public static SampleApplication INSTANCE;
    private Cicerone cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        initCicerone();
    }

    private void initCicerone() {
        cicerone = new Cicerone();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone;
    }

    public Router getRouter() {
        return cicerone;
    }
}
