package ru.terrakok.cicerone;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 29.09.16 17:00.
 */

public interface Navigator {

    void applyNewTransition(Transition transition);

    void rollBackLastTransition();

    void rollBackToScreen(String screenKey);

    void showSystemMessage(String message);

    class Transition {
        private String screenKey;
        private Type transitionType;
        private Object transitionData;

        public Transition(String screenKey, Type transitionType, Object transitionData) {
            this.screenKey = screenKey;
            this.transitionType = transitionType;
            this.transitionData = transitionData;
        }

        public String getScreenKey() {
            return screenKey;
        }

        public Type getTransitionType() {
            return transitionType;
        }

        public Object getTransitionData() {
            return transitionData;
        }

        public enum Type {
            NEW_ROOT,
            NEW_CHAIN,
            DEFAULT
        }
    }
}
