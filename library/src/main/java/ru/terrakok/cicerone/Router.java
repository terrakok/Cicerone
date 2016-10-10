package ru.terrakok.cicerone;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 29.09.16 17:00.
 */

/**
 * Interface for calling the methods of navigation.
 */
public interface Router {

    /**
     * Open new screen and add it to screen chain.
     * @param screenKey screen key
     */
    void navigateTo(String screenKey);

    /**
     * Open new screen and add it to screen chain.
     * @param screenKey screen key
     * @param data initialisation parameters for new screen
     */
    void navigateTo(String screenKey, Object data);

    /**
     * Clear all screens from current chain and open new over root.
     * @param screenKey screen key
     */
    void newScreenChain(String screenKey);

    /**
     * Clear all screens from current chain and open new over root.
     * @param screenKey screen key
     * @param data initialisation parameters for new screen
     */
    void newScreenChain(String screenKey, Object data);

    /**
     * Clear all screens and open new root.
     * @param screenKey screen key
     */
    void newRootScreen(String screenKey);

    /**
     * Clear all screens and open new root.
     * @param screenKey screen key
     * @param data initialisation parameters for new screen
     */
    void newRootScreen(String screenKey, Object data);

    /**
     * Replace current screen.
     * @param screenKey screen key
     */
    void replaceScreen(String screenKey);

    /**
     * Replace current screen.
     * @param screenKey screen key
     * @param data initialisation parameters for new screen
     */
    void replaceScreen(String screenKey, Object data);

    /**
     * Return on screen from chain.
     * If screen key will be not found than return on root.
     * @param screenKey
     */
    void backTo(String screenKey);

    /**
     * Return on previous screen from chain.
     */
    void exit();

    /**
     * Return on previous screen from chain and show system message.
     * @param message system message
     */
    void exitWithMessage(String message);

    /**
     * Show system message.
     * @param message system message
     */
    void showSystemMessage(String message);
}
