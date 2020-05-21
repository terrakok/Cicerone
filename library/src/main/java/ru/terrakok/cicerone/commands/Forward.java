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
    private boolean clearContainer;

    /**
     * Creates a {@link Forward} navigation command.
     *
     * @param screen screen
     * @param clearContainer if FALSE then new screen shows over previous
     */
    public Forward(
            @NotNull Screen screen,
            boolean clearContainer
    ) {
        this.screen = screen;
        this.clearContainer = clearContainer;
    }

    @NotNull
    public Screen getScreen() {
        return screen;
    }

    public boolean isClearContainer() {
        return clearContainer;
    }
}
