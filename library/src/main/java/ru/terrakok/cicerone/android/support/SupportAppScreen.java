package ru.terrakok.cicerone.android.support;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import ru.terrakok.cicerone.Screen;

/**
 * AppScreen is base class for description and creation application screen.<br>
 * NOTE: If you have described the creation of Intent then Activity will be started.<br>
 * Recommendation: Use Intents for launch external application.
 */
public abstract class SupportAppScreen extends Screen {

    public Fragment getFragment() {
        return null;
    }

    public Intent getActivityIntent(Context context) {
        return null;
    }
}
