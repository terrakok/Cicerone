/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.commands;

import org.jetbrains.annotations.NotNull;

import ru.terrakok.cicerone.Screen;

/**
 * Opens new screen.
 */
public class Forward implements Command {
    private Screen screen;

    /**
     * Creates a {@link Forward} navigation command.
     *
     * @param screen screen
     */
    public Forward(@NotNull Screen screen) {
        this.screen = screen;
    }

    @NotNull
    public Screen getScreen() {
        return screen;
    }
}
