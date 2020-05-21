/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
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
    public void navigateTo(@NotNull Screen screen) {
        navigateTo(screen, true);
    }

    /**
     * Open new screen and add it to the screens chain.
     *
     * @param screen screen
     * @param clearContainer if FALSE then new screen shows over previous
     */
    public void navigateTo(@NotNull Screen screen, boolean clearContainer) {
        executeCommands(new Forward(screen, clearContainer));
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screen screen
     */
    public void newRootScreen(@NotNull Screen screen) {
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
    public void replaceScreen(@NotNull Screen screen) {
        executeCommands(new Replace(screen));
    }

    /**
     * Return fragmentBack to the needed screen from the chain.
     * Behavior in the case when no needed screens found depends on
     * the processing of the {@link BackTo} command in a {@link Navigator} implementation.
     *
     * @param screen screen
     */
    public void backTo(@Nullable Screen screen) {
        executeCommands(new BackTo(screen));
    }

    /**
     * Opens several screens inside single transaction.
     *
     * @param screens
     */
    public void newChain(@NotNull Screen... screens) {
        newChain(true, screens);
    }

    /**
     * Opens several screens inside single transaction.
     *
     * @param showOnlyTopScreenView if FALSE then all screen views show together
     * @param screens
     */
    public void newChain(boolean showOnlyTopScreenView, @NotNull Screen... screens) {
        Command[] commands = new Command[screens.length];
        for (int i = 0; i < commands.length; i++) {
            commands[i] = new Forward(screens[i], showOnlyTopScreenView);
        }
        executeCommands(commands);
    }

    /**
     * Clear current stack and open several screens inside single transaction.
     *
     * @param screens
     */
    public void newRootChain(@NotNull Screen... screens) {
        newRootChain(true, screens);
    }

    /**
     * Clear current stack and open several screens inside single transaction.
     *
     * @param showOnlyTopScreenView if FALSE then all screen views show together
     * @param screens
     */
    public void newRootChain(boolean showOnlyTopScreenView, @NotNull Screen... screens) {
        Command[] commands = new Command[screens.length + 1];
        commands[0] = new BackTo(null);
        if (screens.length > 0) {
            commands[1] = new Replace(screens[0]);
            for (int i = 1; i < screens.length; i++) {
                commands[i + 1] = new Forward(screens[i], showOnlyTopScreenView);
            }
        }
        executeCommands(commands);
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
