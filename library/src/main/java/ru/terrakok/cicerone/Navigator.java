package ru.terrakok.cicerone;

import ru.terrakok.cicerone.commands.Command;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * The navigation executor interface.
 */
public interface Navigator {

    /**
     * Apply navigation command
     * @param command navigation command
     */
    void applyCommand(Command command);
}
