package ru.terrakok.cicerone.android.pure;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import ru.terrakok.cicerone.Screen;

/**
 * AppScreen is base class for description and creation application screen. <br/>
 * NOTE: If you have described the creation of Intent then Activity will be started. <br/>
 * Recommendation: Use Intents for launch external application.
 */
public abstract class AppScreen extends Screen {

    public Fragment getFragment() {
        return null;
    }

    public Intent getActivityIntent(Context context) {
        return null;
    }
}
