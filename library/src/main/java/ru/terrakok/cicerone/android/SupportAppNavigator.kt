package ru.terrakok.cicerone.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.widget.Toast
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.CreationalCommand
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

/**
 * Extends [SupportFragmentNavigator] to allow open new or replace current activity.
 *
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 *
 * @author Vasili Chyrvon (vasili.chyrvon@gmail.com)
 * @see AppNavigator
 */
abstract class SupportAppNavigator @JvmOverloads constructor(
        private val activity: FragmentActivity,
        fragmentManager: FragmentManager = activity.supportFragmentManager,
        containerId: Int
) : SupportFragmentNavigator(fragmentManager, containerId) {

    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> applyForward(command)
            is Replace -> applyReplace(command)
            else -> super.applyCommand(command)
        }
    }

    private fun applyForward(forward: Forward) {
        createActivityIntent(activity, forward.screenKey, forward.transitionData)?.let {
            checkAndStartActivity(forward, it)
        }
    }

    private fun applyReplace(replace: Replace) {
        createActivityIntent(activity, replace.screenKey, replace.transitionData)?.let {
            checkAndStartActivity(replace, it)
            activity.finish()
        }
    }

    /**
     * Creates [Intent] to start Activity for [screenKey].
     *
     * If it returns null, [screenKey] will be passed to [createFragment].
     * **Warning:** This method does not work with [BackTo] command.
     *
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return intent to start Activity for the passed screen key, or null if there no activity
     * that accords to passed screen key
     */
    protected abstract fun createActivityIntent(
            context: Context,
            screenKey: String,
            data: Any?
    ): Intent?

    private fun checkAndStartActivity(command: CreationalCommand, activityIntent: Intent) {
        // Check if we can start activity
        activityIntent.resolveActivity(activity.packageManager)?.run {
            val options = createStartActivityOptions(command, activityIntent)
            activity.startActivity(activityIntent, options)
        } ?: unexistingActivity(command.screenKey, activityIntent)
    }

    /**
     * Override this method to create option for start activity
     *
     * @param command        current navigation command. Will be only [Forward] or [Replace]
     * @param activityIntent activity intent
     * @return transition options
     */
    protected open fun createStartActivityOptions(
            command: CreationalCommand,
            activityIntent: Intent
    ): Bundle? {
        @Suppress("DEPRECATION")
        return createStartActivityOptions(command as Command, activityIntent)
    }

    // For backward compatibility
    @Deprecated("use variant of this function with CreationalCommand instead")
    protected open fun createStartActivityOptions(
            command: Command,
            activityIntent: Intent
    ): Bundle? {
        return null
    }

    /**
     * Called when there is no activity to open [screenKey].
     *
     * @param screenKey screen key
     * @param activityIntent intent passed to start Activity for the [screenKey]
     */
    protected open fun unexistingActivity(screenKey: String, activityIntent: Intent) {
        // Do nothing by default
    }

    override fun showSystemMessage(message: String) {
        // Toast by default
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun exit() {
        // Finish by default
        activity.finish()
    }
}
