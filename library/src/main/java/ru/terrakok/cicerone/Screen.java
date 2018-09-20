package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 09.09.18.
 */
public abstract class Screen {
    protected String screenKey = getClass().getSimpleName();

    public String getScreenKey() {
        return screenKey;
    }
}
