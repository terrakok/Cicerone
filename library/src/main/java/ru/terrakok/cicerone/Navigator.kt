package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Command

/**
 * The low-level navigation interface.
 * Navigator is the one who actually performs any transition.
 *
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16
 */
interface Navigator {

    /**
     * Performs transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    fun applyCommand(command: Command)
}
