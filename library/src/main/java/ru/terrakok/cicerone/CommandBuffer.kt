package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Command
import java.util.*

class CommandBuffer : NavigatorHolder {

    private var navigator: Navigator? = null

    private val pendingCommands: Queue<Array<out Command>> = LinkedList()

    override fun setNavigator(navigator: Navigator?) {
        this.navigator = navigator;
        while (!pendingCommands.isEmpty()) {
            if (navigator != null) {
                executeCommands(pendingCommands.poll());
            } else break;
        }
    }

    override fun removeNavigator() {
        navigator = null
    }

    fun <T : Command> executeCommands(commands: Array<T>) {
        if (navigator != null) {
            navigator!!.applyCommands(commands)
        } else {
            pendingCommands.add(commands)
        }
    }
}