package ru.terrakok.cicerone;

/**
 * @author Sergey Boishtyan
 */

public abstract class DefaultNavigator implements Navigator {

    @Override
    public void applyReplace(String screenKey, Object transitionData) {
        //empty
    }

    @Override
    public void applyForward(String screenKey, Object transitionData) {
        //empty
    }

    @Override
    public void applyBack() {
        //empty
    }

    @Override
    public void applyBackTo(String screenKey) {
        //empty
    }

    @Override
    public void applySystemMessage(String message) {
        //empty
    }
}
