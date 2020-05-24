package com.github.terrakok.cicerone

/**
 * Passes navigation command to an active [Navigator]
 * or stores it in the pending commands queue to pass it later.
 */
internal class CommandBuffer : NavigatorHolder {
    private var navigator: Navigator? = null
    private val pendingCommands = mutableListOf<Array<out Command>>()

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        pendingCommands.forEach { navigator.applyCommands(it) }
        pendingCommands.clear()
    }

    override fun removeNavigator() {
        navigator = null
    }

    /**
     * Passes `commands` to the [Navigator] if it available.
     * Else puts it to the pending commands queue to pass it later.
     * @param commands navigation command array
     */
    fun executeCommands(commands: Array<out Command>) {
        navigator?.applyCommands(commands) ?: pendingCommands.add(commands)
    }
}