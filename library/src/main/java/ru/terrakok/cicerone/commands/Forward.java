package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Opens new screen.
 */
public class Forward implements Command {
    private String screenKey;
    private Object transitionData;

    /**
     * Creates a {@link Forward} navigation command.
     *
     * @param screenKey      screen key
     * @param transitionData initial data
     */
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
