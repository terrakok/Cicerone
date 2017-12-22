package ru.terrakok.cicerone.android

import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction

import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.CreationalCommand
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage

/**
 * Implementation of [Navigator] based on fragments.
 *
 * [BackTo] navigation command will return to root if needed screen isn't found in the screens
 * chain. To change this behavior override [backToUnexisting] method.
 *
 * [Back] command will call [exit] method if current screen is root.
 *
 * @param fragmentManager fragment manager
 * @param containerId     id of the fragments container layout
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 * @see SupportFragmentNavigator
 */
abstract class FragmentNavigator(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
) : Navigator {

    override fun applyCommand(command: Command) {
        when (command) {
            is Forward -> applyForward(command)
            is Back -> applyBack()
            is Replace -> applyReplace(command)
            is BackTo -> applyBackTo(command)
            is SystemMessage -> showSystemMessage(command.message)
        }
    }

    private fun applyForward(forward: Forward) {
        val fragment = createFragment(forward.screenKey, forward.transitionData)
        if (fragment != null) {
            openFragment(forward, fragment)
        } else {
            unknownScreen(forward)
        }
    }

    private fun applyBack() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
        } else {
            exit()
        }
    }

    private fun applyReplace(replace: Replace) {
        val fragment = createFragment(replace.screenKey, replace.transitionData) ?: run {
            unknownScreen(replace)
            return
        }

        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
            openFragment(replace, fragment)
        } else {
            openFragment(replace, fragment, addToBackStack = true)
        }
    }

    /**
     * Creates Fragment matching [screenKey].
     *
     * If it returns null, will be called [unknownScreen].
     *
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return instantiated fragment for the passed screen key, or null if there no fragment that
     * accords to passed screen key
     */
    protected abstract fun createFragment(screenKey: String, data: Any?): Fragment?

    private fun openFragment(
            command: CreationalCommand,
            fragment: Fragment,
            addToBackStack: Boolean = true
    ) {
        fragmentManager.makeTransaction {
            setupAnimation(
                    command = command,
                    currentFragment = fragmentManager.findFragmentById(containerId),
                    nextFragment = fragment
            )

            replace(containerId, fragment)
            if (addToBackStack) addToBackStack(command.screenKey)
        }
    }

    /**
     * Called if we can't create a screen.
     */
    protected open fun unknownScreen(command: Command) {
        throw RuntimeException("Can't create a screen for passed screenKey.")
    }

    /**
     * Variant of [setupFragmentTransactionAnimation] where [FragmentTransaction] used as
     * receiver.
     *
     * @receiver fragment transaction
     * @param command             current navigation command. Will be only [Forward] or [Replace]
     * @param currentFragment     current fragment in container (for [Replace] command it will be
     * screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @see setupFragmentTransactionAnimation
     */
    protected fun FragmentTransaction.setupAnimation(
            command: CreationalCommand,
            currentFragment: Fragment,
            nextFragment: Fragment
    ) {
        setupFragmentTransactionAnimation(command, currentFragment, nextFragment, this)
    }

    /**
     * Override this method to setup custom fragment transaction animation.
     *
     * @param command             current navigation command. Will be only [Forward] or [Replace]
     * @param currentFragment     current fragment in container (for [Replace] command it will be
     * screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    protected open fun setupFragmentTransactionAnimation(
            command: CreationalCommand,
            currentFragment: Fragment,
            nextFragment: Fragment,
            fragmentTransaction: FragmentTransaction
    ) {
        @Suppress("DEPRECATION")
        setupFragmentTransactionAnimation(
                command as Command,
                currentFragment,
                nextFragment,
                fragmentTransaction
        )
    }

    // For backward compatibility
    @Deprecated("use variant of this function with CreationalCommand instead")
    protected open fun setupFragmentTransactionAnimation(
            command: Command,
            currentFragment: Fragment,
            nextFragment: Fragment,
            fragmentTransaction: FragmentTransaction
    ) {
        // Do nothing by default
    }

    private fun applyBackTo(backTo: BackTo) {
        backTo.screenKey?.let { key ->
            var hasScreen = false
            for (i in 0 until fragmentManager.backStackEntryCount) {
                if (key == fragmentManager.getBackStackEntryAt(i).name) {
                    fragmentManager.popBackStackImmediate(key, 0)
                    hasScreen = true
                    break
                }
            }

            if (!hasScreen) {
                backToUnexisting()
            }
        } ?: backToRoot()
    }

    /**
     * Called when we tried to back to some specific screen, but didn't found it.
     */
    protected open fun backToUnexisting() {
        backToRoot()
    }

    private fun backToRoot() {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    /**
     * Shows system message.
     *
     * @param message message to show
     */
    protected abstract fun showSystemMessage(message: String)

    /**
     * Called when we try to back from the root.
     */
    protected abstract fun exit()
}
