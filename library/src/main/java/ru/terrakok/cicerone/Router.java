package ru.terrakok.cicerone;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 29.09.16 17:00.
 */

public interface Router {
    void navigateTo(String screenKey);

    void navigateTo(String screenKey, Object data);

    void newScreenChain(String screenKey);

    void newScreenChain(String screenKey, Object data);

    void newRootScreen(String screenKey);

    void newRootScreen(String screenKey, Object data);

    void replaceScreen(String screenKey);

    void replaceScreen(String screenKey, Object data);

    void backTo(String screenKey);

    void exit();

    void exitWithMessage(String message);

    void showSystemMessage(String message);
}
