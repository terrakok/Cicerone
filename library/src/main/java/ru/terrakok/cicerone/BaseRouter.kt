package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Command

/**
 * BaseRouter is an abstract class to implement high-level navigation.
 * Extend it to add needed transition methods.
 *
 * @author Konstantin Tskhovrebov (aka terrakok) on 12.10.16.
 */
abstract class BaseRouter {

    internal val commandBuffer = CommandBuffer()

    /**
     * Sends navigation command to [CommandBuffer].
     *
     * @param command navigation command to execute
     */
    protected fun executeCommand(command: Command) {
        commandBuffer.executeCommand(command)
    }
}
