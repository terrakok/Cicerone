package ru.terrakok.cicerone.commands;

import android.support.annotation.Nullable;

import ru.terrakok.cicerone.Navigator;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Rolls back to the needed screen from the screens chain.
 * Behavior in the case when no needed screens found depends on an implementation of the {@link Navigator}.
 * But the recommended behavior is to return to the root.
 */
public class BackTo implements Command {
    @Nullable
    private String screenKey;

    /**
     * Creates a {@link BackTo} navigation command.
     *
     * @param screenKey screen key or null if you need back to root screen
     */
    public BackTo(@Nullable String screenKey) {
        this.screenKey = screenKey;
    }

    @Nullable
    public String getScreenKey() {
        return screenKey;
    }
}
