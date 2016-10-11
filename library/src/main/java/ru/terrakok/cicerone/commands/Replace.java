package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class Replace implements Command {
    private String screenKey;
    private Object transitionData;

    /**
     * Replace current screen.
     * @param screenKey screen key
     * @param transitionData initial data
     */
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
