package ru.terrakok.cicerone;

import org.jetbrains.annotations.NotNull;

/**
 * Screen is class for description application screen.
 */
public abstract class Screen {
    protected String screenKey = getClass().getCanonicalName();

    @NotNull
    public String getScreenKey() {
        return screenKey;
    }
}
