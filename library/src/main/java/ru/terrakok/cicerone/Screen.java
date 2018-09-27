package ru.terrakok.cicerone;

/**
 * Screen is class for description application screen.
 */
public abstract class Screen {
    protected String screenKey = getClass().getCanonicalName();

    public String getScreenKey() {
        return screenKey;
    }
}
