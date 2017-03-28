package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.AnimRes;

/**
 * Opens new screen.
 */
public class Forward extends AnimationCommand {
    private String screenKey;
    private Object transitionData;

    /**
     * Creates a {@link Forward} navigation command.
     *
     * @param screenKey screen key
     * @param transitionData initial data
     */
    public Forward(String screenKey, Object transitionData) {
        this.screenKey = screenKey;
        this.transitionData = transitionData;
    }

    public Forward(String screenKey, Object transitionData, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        super(enterAnim, exitAnim);
        this.screenKey = screenKey;
        this.transitionData = transitionData;
    }

    public Forward(String screenKey, Object transitionData, @AnimRes int enterAnim, @AnimRes int exitAnim, @AnimRes int popEnterAnim, @AnimRes int popExitAnim) {
        super(enterAnim, exitAnim, popEnterAnim, popExitAnim);
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
