package ru.terrakok.cicerone.commands;

/**
 * Created by terrakok on 29.09.16.
 */

public class Forward implements Command {
    private String screenKey;
    private Object transitionData;

    public Forward(String screenKey, Object transitionData) {
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
