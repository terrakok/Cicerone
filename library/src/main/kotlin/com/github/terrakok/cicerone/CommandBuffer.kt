package com.github.terrakok.cicerone

import android.os.Handler
import android.os.Looper

/**
 * Passes navigation command to an active [Navigator]
 * or stores it in the pending commands queue to pass it later.
 */
internal class CommandBuffer : NavigatorHolder {
    private var navigator: Navigator? = null
    private val pendingCommands = mutableListOf<Array<out Command>>()
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun setNavigator(navigator: Navigator) {
        this.navigator = navigator
        pendingCommands.forEach { navigator.applyCommands(it) }
        pendingCommands.clear()
    }

    override fun removeNavigator() {
        navigator = null
    }

    override fun clearPendingCommands() {
        pendingCommands.clear()
    }

    /**
     * Passes `commands` to the [Navigator] if it available.
     * Else puts it to the pending commands queue to pass it later.
     * @param commands navigation command array
     */
    fun executeCommands(commands: Array<out Command>) {
        mainHandler.post {
            navigator?.applyCommands(commands) ?: pendingCommands.add(commands)
        }
    }
}