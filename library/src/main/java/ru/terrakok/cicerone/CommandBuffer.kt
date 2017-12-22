package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Command
import java.util.*

/**
 * Passes navigation command to an active [Navigator]
 * or stores it in the pending commands queue to pass it later.
 *
 * @author Konstantin Tskhovrebov (aka terrakok) on 12.10.16.
 */
internal class CommandBuffer : NavigatorHolder {

    private var navigator: Navigator? = null
    private val pendingCommands = LinkedList<Command>()

    override fun setNavigator(navigator: Navigator?) {
        this.navigator = navigator

        while (!pendingCommands.isEmpty()) {
            if (navigator == null) break
            executeCommand(pendingCommands.pop())
        }
    }

    override fun removeNavigator() {
        this.navigator = null
    }

    /**
     * Passes [command] to the [Navigator] if it available.
     * Else puts it to the pending commands queue to pass it later.
     *
     * @param command navigation command
     */
    fun executeCommand(command: Command) {
        navigator?.applyCommand(command) ?: pendingCommands.add(command)
    }
}
