package ru.terrakok.cicerone;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 29.09.16 17:00.
 */

import java.util.LinkedList;
import java.util.Queue;

public class Cicerone implements Router, NavigatorHolder {
    private Navigator navigator;
    private Queue<RouterCommand> pendingCommands = new LinkedList<>();

    public Router getRouter() {
        return this;
    }

    public NavigatorHolder getNavigatorHolder() {
        return this;
    }

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
        while (!pendingCommands.isEmpty()) {
            executeCommand(pendingCommands.poll());
        }
    }

    @Override
    public void clearNavigator() {
        this.navigator = null;
    }

    @Override
    public void navigateTo(String screenKey) {
        navigateTo(screenKey, null);
    }

    @Override
    public void navigateTo(String screenKey, Object data) {
        executeCommand(new RouterCommand(
                RouterCommandType.NAVIGATE_TO,
                screenKey,
                data,
                null
        ));
    }

    @Override
    public void newScreenChain(String screenKey) {
        newScreenChain(screenKey, null);
    }

    @Override
    public void newScreenChain(String screenKey, Object data) {
        executeCommand(new RouterCommand(
                RouterCommandType.NEW_SCREEN_CHAIN,
                screenKey,
                data,
                null
        ));
    }

    @Override
    public void newRootScreen(String screenKey) {
        newRootScreen(screenKey, null);
    }

    @Override
    public void newRootScreen(String screenKey, Object data) {
        executeCommand(new RouterCommand(
                RouterCommandType.NEW_ROOT_SCREEN,
                screenKey,
                data,
                null
        ));
    }

    @Override
    public void replaceScreen(String screenKey) {
        replaceScreen(screenKey, null);
    }

    @Override
    public void replaceScreen(String screenKey, Object data) {
        executeCommand(new RouterCommand(
                RouterCommandType.REPLACE_SCREEN,
                screenKey,
                data,
                null
        ));
    }

    @Override
    public void backToScreen(String screenKey) {
        executeCommand(new RouterCommand(
                RouterCommandType.BACK_TO_SCREEN,
                screenKey,
                null,
                null
        ));
    }

    @Override
    public void exit() {
        executeCommand(new RouterCommand(
                RouterCommandType.EXIT,
                null,
                null,
                null
        ));
    }

    @Override
    public void exitWithMessage(String message) {
        executeCommand(new RouterCommand(
                RouterCommandType.EXIT_WITH_MESSAGE,
                null,
                null,
                message
        ));
    }

    @Override
    public void showSystemMessage(String message) {
        executeCommand(new RouterCommand(
                RouterCommandType.SHOW_SYSTEM_MESSAGE,
                null,
                null,
                message
        ));
    }

    private void executeCommand(RouterCommand command) {
        if (navigator != null) {
            switch (command.type) {
                case NAVIGATE_TO:
                    navigator.applyNewTransition(new Navigator.Transition(command.screenKey, Navigator.Transition.Type.DEFAULT, command.data));
                    break;
                case NEW_SCREEN_CHAIN:
                    navigator.applyNewTransition(new Navigator.Transition(command.screenKey, Navigator.Transition.Type.NEW_CHAIN, command.data));
                    break;
                case NEW_ROOT_SCREEN:
                    navigator.applyNewTransition(new Navigator.Transition(command.screenKey, Navigator.Transition.Type.NEW_ROOT, command.data));
                    break;
                case REPLACE_SCREEN:
                    navigator.rollBackLastTransition();
                    navigator.applyNewTransition(new Navigator.Transition(command.screenKey, Navigator.Transition.Type.DEFAULT, command.data));
                    break;
                case BACK_TO_SCREEN:
                    navigator.rollBackToScreen(command.screenKey);
                    break;
                case EXIT:
                    navigator.rollBackLastTransition();
                    break;
                case EXIT_WITH_MESSAGE:
                    navigator.rollBackLastTransition();
                    navigator.showSystemMessage(command.message);
                    break;
                case SHOW_SYSTEM_MESSAGE:
                    navigator.showSystemMessage(command.message);
                    break;
            }
        } else {
            pendingCommands.add(command);
        }
    }

    private enum RouterCommandType {
        NAVIGATE_TO,
        NEW_SCREEN_CHAIN,
        NEW_ROOT_SCREEN,
        REPLACE_SCREEN,
        BACK_TO_SCREEN,
        EXIT,
        EXIT_WITH_MESSAGE,
        SHOW_SYSTEM_MESSAGE
    }

    private static class RouterCommand {
        private RouterCommandType type;
        private String screenKey;
        private Object data;
        private String message;

        RouterCommand(RouterCommandType type, String screenKey, Object data, String message) {
            this.type = type;
            this.screenKey = screenKey;
            this.data = data;
            this.message = message;
        }

        @Override
        public String toString() {
            return type.toString() + " ["
                    + screenKey
                    + (message != null ? (" (" + message + ")") : "")
                    + "]";
        }
    }
}
