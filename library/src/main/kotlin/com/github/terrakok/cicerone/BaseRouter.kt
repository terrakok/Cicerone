package com.github.terrakok.cicerone

/**
 * BaseRouter is an abstract class to implement high-level navigation.
 *
 * Extend it to add needed transition methods.
 */
abstract class BaseRouter(private val resultWire: ResultWire = ResultWire()) {
    internal val commandBuffer = CommandBuffer()


    /**
     * Sets data listener with given key
     * and returns [ResultListenerHandler] for availability to dispose subscription.
     *
     * After first call listener will be removed.
     */
    fun setResultListener(
        key: String,
        listener: ResultListener
    ): ResultListenerHandler {
        return resultWire.setResultListener(key, listener)
    }

    /**
     * Sends data to listener with given key.
     */
    fun sendResult(key: String, data: Any) {
        resultWire.sendResult(key, data)
    }

    /**
     * Sends navigation command array to [CommandBuffer].
     *
     * @param commands navigation command array to execute
     */
    protected fun executeCommands(vararg commands: Command) {
        commandBuffer.executeCommands(commands)
    }
}