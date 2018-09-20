/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.commands;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Screen;

/**
 * Rolls fragmentBack to the needed screen from the screens chain.
 * Behavior in the case when no needed screens found depends on an implementation of the {@link Navigator}.
 * But the recommended behavior is to return to the root.
 */
public class BackTo implements Command {
    private Screen screen;

    /**
     * Creates a {@link BackTo} navigation command.
     *
     * @param screen screen
     */
    public BackTo(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
