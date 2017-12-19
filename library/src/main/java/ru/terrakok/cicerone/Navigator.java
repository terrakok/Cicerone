/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone;

import ru.terrakok.cicerone.commands.Command;

/**
 * The low-level navigation interface.
 * Navigator is the one who actually performs any transition.
 */
public interface Navigator {

    /**
     * Performs transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    void applyCommand(Command command);
}
