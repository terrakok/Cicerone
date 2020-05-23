package com.github.terrakok.cicerone.androidx

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.commands.*

/**
 * Navigator implementation for launch fragments and activities.
 *
 * Feature [BackTo] works only for fragments.
 *
 * Recommendation: most useful for Single-Activity application.
 */
open class AppNavigator @JvmOverloads constructor(
    protected val activity: FragmentActivity,
    protected val containerId: Int,
    protected val fragmentManager: FragmentManager = activity.supportFragmentManager
) : Navigator {

    protected val localStackCopy = mutableListOf<TransactionInfo>()

    override fun applyCommands(commands: Array<out Command>) {
        fragmentManager.executePendingTransactions()

        //copy stack before apply commands
        copyStackToLocal()

        for (command in commands) {
            try {
                applyCommand(command)
            } catch (e: RuntimeException) {
                errorOnApplyCommand(command, e)
            }
        }
    }

    private fun copyStackToLocal() {
        localStackCopy.clear()
        for (i in 0 until fragmentManager.backStackEntryCount) {
            val str = fragmentManager.getBackStackEntryAt(i).name
            localStackCopy.add(TransactionInfo.fromString(str))
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected open fun applyCommand(command: Command) {
        when (command) {
            is Forward -> activityForward((command))
            is Replace -> activityReplace((command))
            is BackTo -> backTo((command))
            is Back -> fragmentBack()
        }
    }

    protected open fun activityForward(command: Forward) {
        val screen = command.screen as AppScreen
        val activityIntent = screen.getActivityIntent(activity)

        // Start activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(screen, activityIntent, options)
        } else {
            fragmentForward(command)
        }
    }

    protected open fun fragmentForward(command: Forward) {
        val screen = command.screen as AppScreen
        val fragmentParams = screen.getFragmentParams()
        val fragment = if (fragmentParams == null) createFragment(screen) else null
        val type = if (command.clearContainer) TransactionInfo.Type.REPLACE else TransactionInfo.Type.ADD
        fragmentForwardInternal(
            command,
            TransactionInfo(screen.screenKey, type),
            fragment,
            fragmentParams
        )
    }

    private fun fragmentForwardInternal(
        command: Command,
        transactionInfo: TransactionInfo,
        fragment: Fragment?,
        fragmentParams: FragmentParams?
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            fragmentParams,
            fragmentTransaction
        )
        addNewFragmentToTransaction(
            fragmentTransaction,
            transactionInfo,
            fragment,
            fragmentParams
        )
        fragmentTransaction
            .addToBackStack(transactionInfo.toString())
            .commit()
        localStackCopy.add(transactionInfo)
    }

    protected open fun fragmentBack() {
        if (localStackCopy.isNotEmpty()) {
            fragmentManager.popBackStack()
            localStackCopy.removeAt(localStackCopy.lastIndex)
        } else {
            activityBack()
        }
    }

    protected open fun activityBack() {
        activity.finish()
    }

    protected open fun activityReplace(command: Replace) {
        val screen = command.screen as AppScreen
        val activityIntent = screen.getActivityIntent(activity)

        // Replace activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(screen, activityIntent, options)
            activity.finish()
        } else {
            fragmentReplace(command)
        }
    }

    protected open fun fragmentReplace(command: Replace) {
        val screen = command.screen as AppScreen
        val fragmentParams = screen.getFragmentParams()
        val fragment = if (fragmentParams == null) createFragment(screen) else null
        if (localStackCopy.isNotEmpty()) {
            fragmentManager.popBackStack()
            val removedTransactionInfo = localStackCopy.removeAt(localStackCopy.lastIndex)
            fragmentForwardInternal(
                command,
                TransactionInfo(screen.screenKey, removedTransactionInfo.type),
                fragment,
                fragmentParams
            )
        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()
            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentParams,
                fragmentTransaction
            )
            addNewFragmentToTransaction(
                fragmentTransaction,
                TransactionInfo(screen.screenKey, TransactionInfo.Type.REPLACE),
                fragment,
                fragmentParams
            )
            fragmentTransaction.commit()
        }
    }

    private fun addNewFragmentToTransaction(
        transaction: FragmentTransaction,
        transactionInfo: TransactionInfo,
        fragment: Fragment?,
        params: FragmentParams?
    ) {
        when (transactionInfo.type) {
            TransactionInfo.Type.ADD -> when {
                params != null -> transaction.add(containerId, params.fragmentClass, params.arguments)
                fragment != null -> transaction.add(containerId, fragment)
                else -> throw IllegalArgumentException(
                    "Either 'params' or 'fragment' shouldn't be null for ${transactionInfo.screenKey}"
                )
            }
            TransactionInfo.Type.REPLACE -> when {
                params != null -> transaction.replace(containerId, params.fragmentClass, params.arguments)
                fragment != null -> transaction.replace(containerId, fragment)
                else -> throw IllegalArgumentException(
                    "Either 'params' or 'fragment' shouldn't be null for ${transactionInfo.screenKey}"
                )
            }
        }
    }

    /**
     * Performs [BackTo] command transition
     */
    protected open fun backTo(command: BackTo) {
        if (command.screen == null) {
            backToRoot()
        } else {
            val screenKey = command.screen.screenKey
            val index = localStackCopy.indexOfFirst { it.screenKey == screenKey }
            if (index != -1) {
                val forRemove = localStackCopy.subList(index, localStackCopy.size)
                fragmentManager.popBackStack(forRemove.first().toString(), 0)
                forRemove.clear()
            } else {
                backToUnexisting(command.screen as AppScreen)
            }
        }
    }

    private fun backToRoot() {
        localStackCopy.clear()
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /**
     * Override this method to setup fragment transaction [FragmentTransaction].
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param command             current navigation command. Will be only [Forward] or [Replace]
     * @param currentFragment     current fragment in container
     *                            (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param nextFragmentParams  next screen fragment parameters
     * @param fragmentTransaction fragment transaction
     */
    protected open fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        nextFragmentParams: FragmentParams?,
        fragmentTransaction: FragmentTransaction
    ) {
        // Do nothing by default
    }

    /**
     * Override this method to create option for start activity
     *
     * @param command        current navigation command. Will be only [Forward] or [Replace]
     * @param activityIntent activity intent
     * @return transition options
     */
    protected open fun createStartActivityOptions(
        command: Command,
        activityIntent: Intent
    ): Bundle? = null

    private fun checkAndStartActivity(
        screen: AppScreen,
        activityIntent: Intent,
        options: Bundle?
    ) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(activityIntent, options)
        } else {
            unexistingActivity(screen, activityIntent)
        }
    }

    /**
     * Called when there is no activity to open `screenKey`.
     *
     * @param screen         screen
     * @param activityIntent intent passed to start Activity for the `screenKey`
     */
    protected open fun unexistingActivity(
        screen: AppScreen,
        activityIntent: Intent
    ) {
        // Do nothing by default
    }

    /**
     * Creates Fragment matching `screenKey`.
     *
     * @param screen screen
     * @return instantiated fragment for the passed screen
     */
    protected open fun createFragment(screen: AppScreen): Fragment {
        return screen.getFragment() ?: run {
            errorWhileCreatingScreen(screen)
            throw RuntimeException("Can't create a screen: " + screen.screenKey)
        }
    }

    /**
     * Called when we tried to fragmentBack to some specific screen (via [BackTo] command),
     * but didn't found it.
     *
     * @param screen screen
     */
    protected open fun backToUnexisting(screen: AppScreen) {
        backToRoot()
    }

    /**
     * Called when we tried to create new intent or fragment but didn't receive them.
     *
     * @param screen screen
     */
    protected open fun errorWhileCreatingScreen(screen: AppScreen) {
        // Do nothing by default
    }

    /**
     * Override this method if you want to handle apply command error.
     *
     * @param command command
     * @param error   error
     */
    protected open fun errorOnApplyCommand(
        command: Command,
        error: RuntimeException
    ) {
        throw error
    }
}