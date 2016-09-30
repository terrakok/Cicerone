package ru.terrakok.cicerone;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 29.09.16 17:00.
 */

import java.util.LinkedList;
import java.util.Queue;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackToScreen;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.NewChain;
import ru.terrakok.cicerone.commands.NewRoot;
import ru.terrakok.cicerone.commands.SystemMessage;

public class Cicerone implements Router, NavigatorHolder {
    private Navigator navigator;
    private Queue<Command> pendingCommands = new LinkedList<>();

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
        if (navigator != null) {
            while (!pendingCommands.isEmpty()) {
                executeCommand(pendingCommands.poll());
            }
        }
    }

    @Override
    public void removeNavigator() {
        this.navigator = null;
    }

    @Override
    public void navigateTo(String screenKey) {
        navigateTo(screenKey, null);
    }

    @Override
    public void navigateTo(String screenKey, Object data) {
        executeCommand(new Forward(screenKey, data));
    }

    @Override
    public void newScreenChain(String screenKey) {
        newScreenChain(screenKey, null);
    }

    @Override
    public void newScreenChain(String screenKey, Object data) {
        executeCommand(new NewChain(screenKey, data));
    }

    @Override
    public void newRootScreen(String screenKey) {
        newRootScreen(screenKey, null);
    }

    @Override
    public void newRootScreen(String screenKey, Object data) {
        executeCommand(new NewRoot(screenKey, data));
    }

    @Override
    public void replaceScreen(String screenKey) {
        replaceScreen(screenKey, null);
    }

    @Override
    public void replaceScreen(String screenKey, Object data) {
        executeCommand(new Back());
        executeCommand(new Forward(screenKey, data));
    }

    @Override
    public void backToScreen(String screenKey) {
        executeCommand(new BackToScreen(screenKey));
    }

    @Override
    public void exit() {
        executeCommand(new Back());
    }

    @Override
    public void exitWithMessage(String message) {
        executeCommand(new Back());
        executeCommand(new SystemMessage(message));
    }

    @Override
    public void showSystemMessage(String message) {
        executeCommand(new SystemMessage(message));
    }

    private void executeCommand(Command command) {
        if (navigator != null) {
            navigator.applyCommand(command);
        } else {
            pendingCommands.add(command);
        }
    }
}
