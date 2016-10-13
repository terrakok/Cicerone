package ru.terrakok.cicerone;

import ru.terrakok.cicerone.commands.Command;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

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
