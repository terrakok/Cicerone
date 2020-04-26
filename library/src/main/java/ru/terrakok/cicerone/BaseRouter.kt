package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Command

abstract class BaseRouter() {

    val commandBuffer = CommandBuffer()

    protected open fun <T : Command> executeCommands(vararg commands: T) {
        commandBuffer.executeCommands(commands)
    }
}