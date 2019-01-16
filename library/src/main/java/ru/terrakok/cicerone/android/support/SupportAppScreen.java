package ru.terrakok.cicerone.android.support;

import android.content.Context;
import android.content.Intent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import androidx.fragment.app.Fragment;
import ru.terrakok.cicerone.Screen;

/**
 * AppScreen is base class for description and creation application screen.<br>
 * NOTE: If you have described the creation of Intent then Activity will be started.<br>
 * Recommendation: Use Intents for launch external application.
 */
public abstract class SupportAppScreen extends Screen {

    @Nullable
    public Fragment getFragment() {
        return null;
    }

    @Nullable
    public Intent getActivityIntent(@NotNull Context context) {
        return null;
    }
}
