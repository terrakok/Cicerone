package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class BackTo implements Command{
    private String screenKey;

    /**
     * Back to screen from current chain if it exist or return on root screen.
     * @param screenKey screen key
     */
    public BackTo(String screenKey) {
        this.screenKey = screenKey;
    }

    public String getScreenKey() {
        return screenKey;
    }
}
