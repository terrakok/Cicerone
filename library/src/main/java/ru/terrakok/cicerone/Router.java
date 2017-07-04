package ru.terrakok.cicerone;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;
import ru.terrakok.cicerone.result.ResultData;
import ru.terrakok.cicerone.result.ResultListener;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 12.10.16
 */

/**
 * Router is the class for high-level navigation.
 * Use it to perform needed transitions.<br>
 * This implementation covers almost all cases needed for the average app.
 * Extend it if you need some tricky navigation.
 */
public class Router extends BaseRouter {

    private HashMap<Integer, WeakReference<ResultListener>> resultListeners = new HashMap<>();

    public Router() {
        super();
    }

    public void listenResult(Integer requestCode, ResultListener listener) {
        resultListeners.put(requestCode, new WeakReference<>(listener));
    }

    protected boolean sendResult(Integer requestCode, ResultData result) {
        if (resultListeners.containsKey(requestCode)) {
            ResultListener resultListener = resultListeners.get(requestCode).get();
            if (resultListener != null) {
                resultListener.onResult(result);
                return true;
            }
        }
        return false;
    }

    /**
     * Open new screen and add it to the screens chain.
     *
     * @param screenKey screen key
     */
    public void navigateTo(String screenKey) {
        navigateTo(screenKey, null);
    }

    /**
     * Open new screen and add it to screens chain.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen
     */
    public void navigateTo(String screenKey, Object data) {
        executeCommand(new Forward(screenKey, data));
    }

    /**
     * Clear the current screens chain and start new one
     * by opening a new screen right after the root.
     *
     * @param screenKey screen key
     */
    public void newScreenChain(String screenKey) {
        newScreenChain(screenKey, null);
    }

    /**
     * Clear the current screens chain and start new one
     * by opening a new screen right after the root.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen
     */
    public void newScreenChain(String screenKey, Object data) {
        executeCommand(new BackTo(null));
        executeCommand(new Forward(screenKey, data));
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screenKey screen key
     */
    public void newRootScreen(String screenKey) {
        newRootScreen(screenKey, null);
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the root
     */
    public void newRootScreen(String screenKey, Object data) {
        executeCommand(new BackTo(null));
        executeCommand(new Replace(screenKey, data));
    }

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack,
     * so by going back you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screenKey screen key
     */
    public void replaceScreen(String screenKey) {
        replaceScreen(screenKey, null);
    }

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack,
     * so by going back you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen
     */
    public void replaceScreen(String screenKey, Object data) {
        executeCommand(new Replace(screenKey, data));
    }

    /**
     * Return back to the needed screen from the chain.
     * Behavior in the case when no needed screens found depends on
     * the processing of the {@link BackTo} command in a {@link Navigator} implementation.
     *
     * @param screenKey screen key
     */
    public void backTo(String screenKey) {
        executeCommand(new BackTo(screenKey));
    }

    /**
     * Remove all screens from the chain and exit.
     * It's mostly used to finish the application or close a supplementary navigation chain.
     */
    public void finishChain() {
        executeCommand(new BackTo(null));
        executeCommand(new Back());
    }

    /**
     * Return to the previous screen in the chain.
     * Behavior in the case when the current screen is the root depends on
     * the processing of the {@link Back} command in a {@link Navigator} implementation.
     */
    public void exit() {
        executeCommand(new Back());
    }

    public void exitWithResult(Integer requestCode, ResultData result) {
        sendResult(requestCode, result);
        exit();
    }

    /**
     * Return to the previous screen in the chain and show system message.
     *
     * @param message message to show
     */
    public void exitWithMessage(String message) {
        executeCommand(new Back());
        executeCommand(new SystemMessage(message));
    }

    /**
     * Show system message.
     *
     * @param message message to show
     */
    public void showSystemMessage(String message) {
        executeCommand(new SystemMessage(message));
    }
}
