package ru.terrakok.cicerone.android.support;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.terrakok.cicerone.Screen;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 09.09.18.
 */
public abstract class SupportAppScreen extends Screen {

    public Fragment getFragment() {
        return null;
    }

    public Intent getActivityIntent(Context context) {
        return null;
    }
}
