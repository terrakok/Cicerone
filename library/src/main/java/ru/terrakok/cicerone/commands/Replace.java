package ru.terrakok.cicerone.commands;

/**
 * Created by terrakok on 29.09.16.
 */

public class Replace implements Command {
    private String screenKey;
    private Object transitionData;

    public Replace(String screenKey, Object transitionData) {
        this.screenKey = screenKey;
        this.transitionData = transitionData;
    }

    public String getScreenKey() {
        return screenKey;
    }

    public Object getTransitionData() {
        return transitionData;
    }
}
