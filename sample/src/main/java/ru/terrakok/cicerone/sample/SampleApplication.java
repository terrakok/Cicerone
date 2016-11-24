package ru.terrakok.cicerone.sample;

import android.app.Application;

import ru.terrakok.cicerone.sample.dagger.AppComponent;
import ru.terrakok.cicerone.sample.dagger.DaggerAppComponent;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class SampleApplication extends Application {
    public static SampleApplication INSTANCE;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().build();
        }
        return appComponent;
    }
}
