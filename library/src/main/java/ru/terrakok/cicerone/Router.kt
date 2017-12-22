package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage
import ru.terrakok.cicerone.result.ResultListener

/**
 * Router is the class for high-level navigation.
 * Use it to perform needed transitions.
 *
 * This implementation covers almost all cases needed for the average app.
 * Extend it if you need some tricky navigation.
 *
 * @author Konstantin Tskhovrebov (aka terrakok) on 12.10.16.
 */
open class Router : BaseRouter() {

    private val resultListeners: MutableMap<Int, ResultListener> = hashMapOf()

    /**
     * Subscribe to the screen result.
     *
     * **Note:** only one listener can subscribe to a unique [resultCode]!
     * You must call a [removeResultListener] to avoid a memory leak.
     *
     * @param resultCode key for filter results
     * @param listener   result listener
     */
    open fun setResultListener(resultCode: Int, listener: ResultListener) {
        resultListeners.put(resultCode, listener)
    }

    /**
     * Unsubscribe from the screen result.
     *
     * @param resultCode key for filter results
     */
    open fun removeResultListener(resultCode: Int) {
        resultListeners.remove(resultCode)
    }

    /**
     * Send result data to subscriber.
     *
     * @param resultCode result data key
     * @param result     result data
     * @return TRUE if listener was notified and FALSE otherwise
     */
    protected open fun sendResult(resultCode: Int, result: Any): Boolean {
        val resultListener = resultListeners[resultCode]
        if (resultListener != null) {
            resultListener.onResult(result)
            return true
        }

        return false
    }

    /**
     * Open new screen and add it to screens chain.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen, can be null
     */
    @JvmOverloads
    open fun navigateTo(screenKey: String, data: Any? = null) {
        executeCommand(Forward(screenKey, data))
    }

    /**
     * Clear the current screens chain and start new one
     * by opening a new screen right after the root.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen, can be null
     */
    @JvmOverloads
    open fun newScreenChain(screenKey: String, data: Any? = null) {
        executeCommand(BackTo(null))
        executeCommand(Forward(screenKey, data))
    }

    /**
     * Clear all screens and open new one as root.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the root, can be null
     */
    @JvmOverloads
    open fun newRootScreen(screenKey: String, data: Any? = null) {
        executeCommand(BackTo(null))
        executeCommand(Replace(screenKey, data))
    }

    /**
     * Replace current screen.
     *
     * By replacing the screen, you alters the backstack,
     * so by going back you will return to the previous screen
     * and not to the replaced one.
     *
     * @param screenKey screen key
     * @param data      initialisation parameters for the new screen, can be null
     */
    @JvmOverloads
    open fun replaceScreen(screenKey: String, data: Any? = null) {
        executeCommand(Replace(screenKey, data))
    }

    /**
     * Return back to the needed screen from the chain.
     *
     * Behavior in the case when no needed screens found depends on
     * the processing of the [BackTo] command in a [Navigator] implementation.
     *
     * @param screenKey screen key
     */
    open fun backTo(screenKey: String) {
        executeCommand(BackTo(screenKey))
    }

    /**
     * Remove all screens from the chain and exit.
     *
     * It's mostly used to finish the application or close a supplementary navigation chain.
     */
    open fun finishChain() {
        executeCommand(BackTo(null))
        executeCommand(Back())
    }

    /**
     * Return to the previous screen in the chain.
     *
     * Behavior in the case when the current screen is the root depends on
     * the processing of the [Back] command in a [Navigator] implementation.
     */
    open fun exit() {
        executeCommand(Back())
    }

    /**
     * Return to the previous screen in the chain and send result data.
     *
     * @param resultCode result data key
     * @param result     result data
     */
    open fun exitWithResult(resultCode: Int, result: Any) {
        exit()
        sendResult(resultCode, result)
    }

    /**
     * Return to the previous screen in the chain and show system message.
     *
     * @param message message to show
     */
    open fun exitWithMessage(message: String) {
        executeCommand(Back())
        executeCommand(SystemMessage(message))
    }

    /**
     * Show system message.
     *
     * @param message message to show
     */
    open fun showSystemMessage(message: String) {
        executeCommand(SystemMessage(message))
    }
}
