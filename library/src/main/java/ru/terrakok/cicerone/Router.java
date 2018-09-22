/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Router is the class for high-level navigation.
 * Use it to perform needed transitions.<br>
 * This implementation covers almost all cases needed for the average app.
 * Extend it if you need some tricky navigation.
 */
public class Router extends BaseRouter {

    public Router() {
        super();
    }

    /**
     * Open new screen and add it to the screens chain.
     *
     * @param screen screen
     */
    public void navigateTo(Screen screen) {
        executeCommands(new Forward(screen));
    }

    /**
     * Clear the current screens chain and start new one
     * by opening a new screen right after the root.
     *
     * @param screen screen
     */
    public void newScreenChain(Screen screen) {
        executeCommands(
                new BackTo(null),
                new Forward(screen)
        );
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screen screen
     */
    public void newRootScreen(Screen screen) {
        executeCommands(
                new BackTo(null),
                new Replace(screen)
        );
    }

    /**
     * Replace current screen.
     * By replacing the screen, you alters the backstack,
     * so by going fragmentBack you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screen screen
     */
    public void replaceScreen(Screen screen) {
        executeCommands(new Replace(screen));
    }

    /**
     * Return fragmentBack to the needed screen from the chain.
     * Behavior in the case when no needed screens found depends on
     * the processing of the {@link BackTo} command in a {@link Navigator} implementation.
     *
     * @param screen screen
     */
    public void backTo(Screen screen) {
        executeCommands(new BackTo(screen));
    }

    /**
     * Remove all screens from the chain and exit.
     * It's mostly used to finish the application or close a supplementary navigation chain.
     */
    public void finishChain() {
        executeCommands(
                new BackTo(null),
                new Back()
        );
    }

    /**
     * Return to the previous screen in the chain.
     * Behavior in the case when the current screen is the root depends on
     * the processing of the {@link Back} command in a {@link Navigator} implementation.
     */
    public void exit() {
        executeCommands(new Back());
    }

}
