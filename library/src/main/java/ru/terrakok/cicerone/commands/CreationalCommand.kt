package ru.terrakok.cicerone.commands

/**
 * Navigation command as result of which you will get created screen.
 */
interface CreationalCommand : Command {

    /**
     * Identifier of screen
     */
    val screenKey: String

    /**
     * Data for screen initialization, can be null
     */
    val transitionData: Any?

}
