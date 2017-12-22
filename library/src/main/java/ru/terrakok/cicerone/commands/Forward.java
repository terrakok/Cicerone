package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Opens new screen.
 */
public class Forward implements Command {
    @NonNull
    private String screenKey;
    @Nullable
    private Object transitionData;

    /**
     * Creates a {@link Forward} navigation command.
     *
     * @param screenKey      screen key
     * @param transitionData initial data, can be null
     */
    public Forward(@NonNull String screenKey, @Nullable Object transitionData) {
        this.screenKey = screenKey;
        this.transitionData = transitionData;
    }

    @NonNull
    public String getScreenKey() {
        return screenKey;
    }

    @Nullable
    public Object getTransitionData() {
        return transitionData;
    }
}
