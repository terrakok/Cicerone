/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.commands;

import org.jetbrains.annotations.NotNull;

import ru.terrakok.cicerone.Screen;

/**
 * Replaces the current screen.
 */
public class Replace implements Command {
    @NotNull
    private Screen screen;

    /**
     * Creates a {@link Replace} navigation command.
     *
     * @param screen screen
     */
    public Replace(@NotNull Screen screen) {
        this.screen = screen;
    }

    @NotNull
    public Screen getScreen() {
        return screen;
    }
}
