package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 09.09.18.
 */
public abstract class Screen {
    private String screenKey;

    public Screen(String screenKey) {
        this.screenKey = screenKey;
    }

    public String getScreenKey() {
        return screenKey;
    }
}
