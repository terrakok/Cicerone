package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * The low-level navigation interface.
 * Navigator is the one who actually performs any transition.
 */
public interface Navigator {

    void applyReplace(String screenKey, Object transitionData);

    void applyForward(String screenKey, Object transitionData);

    void applyBack();

    void applyBackTo(String screenKey);

    void applySystemMessage(String message);
}
