package ru.terrakok.cicerone.android.aac;

import android.os.Bundle;
import android.support.annotation.IdRes;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import ru.terrakok.cicerone.Screen;

public abstract class AacScreen extends Screen {

    /**
     * It could be actionId or destinationId
     */
    @IdRes
    public abstract int getNavigationResId();

    public Bundle getArgs() {
        return null;
    }

    public NavOptions getNavOptions() {
        return null;
    }

    public Navigator.Extras getNavExtras() {
        return null;
    }
}
