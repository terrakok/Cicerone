package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import ru.terrakok.cicerone.Screen;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 09.09.18.
 */
public abstract class AppScreen extends Screen {

    public AppScreen(String screenKey) {
        super(screenKey);
    }

    public Fragment getFragment() {
        return null;
    }

    public Intent getActivityIntent(Context context) {
        return null;
    }
}
