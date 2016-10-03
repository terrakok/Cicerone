package ru.terrakok.cicerone.commands;

/**
 * Created by terrakok on 29.09.16.
 */

public class BackTo implements Command{
    private String screenKey;

    public BackTo(String screenKey) {
        this.screenKey = screenKey;
    }

    public String getScreenKey() {
        return screenKey;
    }
}
