package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * The main navigation interface.  
 * Use it to perform needed transitions.
 */
public interface Router {

    /**
     * Open new screen and add it to the screens chain.
     * @param screenKey screen key
     */
    void navigateTo(String screenKey);

    /**
     * Open new screen and add it to screens chain.
     * @param screenKey screen key
     * @param data initialisation parameters for the new screen
     */
    void navigateTo(String screenKey, Object data);

    /**
     * Clear the current screens chain and start new one 
     * by opening a new screen right after the root.
     * @param screenKey screen key
     */
    void newScreenChain(String screenKey);

    /**
     * Clear the current screens chain and start new one 
     * by opening a new screen right after the root.
     * @param screenKey screen key
     * @param data initialisation parameters for the new screen
     */
    void newScreenChain(String screenKey, Object data);

    /**
     * Clear all screens and open new one as root.
     * @param screenKey screen key
     */
    void newRootScreen(String screenKey);

    /**
     * Clear all screens and open new one as root.
     * @param screenKey screen key
     * @param data initialisation parameters for the root
     */
    void newRootScreen(String screenKey, Object data);

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack, 
     * so by going back you will return to the previous screen 
     * and not to the replaced one.
     * @param screenKey screen key
     */
    void replaceScreen(String screenKey);

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack, 
     * so by going back you will return to the previous screen 
     * and not to the replaced one.
     * @param screenKey screen key
     * @param data initialisation parameters for the new screen
     */
    void replaceScreen(String screenKey, Object data);

    /**
     * Return to the previous screen in the chain.
     * If no screens with passed screenKey will be found then return to the root.
     * @param screenKey
     */
    void backTo(String screenKey);

    /**
     * Return to the previous screen in the chain.
     */
    void exit();

    /**
     * Return to the previous screen in the chain and show system message.
     * @param message message to show
     */
    void exitWithMessage(String message);

    /**
     * Show system message.
     * @param message message to show
     */
    void showSystemMessage(String message);
}
