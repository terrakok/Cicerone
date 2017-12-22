package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Replaces the current screen.
 */
public class Replace implements Command {
    @NonNull
    private String screenKey;
    @Nullable
    private Object transitionData;

    /**
     * Creates a {@link Replace} navigation command.
     *
     * @param screenKey      screen key
     * @param transitionData initial data, can be null
     */
    public Replace(@NonNull String screenKey, @Nullable Object transitionData) {
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
