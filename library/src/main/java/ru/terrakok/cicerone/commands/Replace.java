/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.commands;

/**
 * Replaces the current screen.
 */
public class Replace implements Command {
    private String screenKey;
    private Object transitionData;

    /**
     * Creates a {@link Replace} navigation command.
     *
     * @param screenKey      screen key
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
