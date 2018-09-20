/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.commands;

import ru.terrakok.cicerone.Screen;

/**
 * Replaces the current screen.
 */
public class Replace implements Command {
    private Screen screen;

    /**
     * Creates a {@link Replace} navigation command.
     *
     * @param screen screen
     */
    public Replace(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
