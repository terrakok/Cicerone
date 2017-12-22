package ru.terrakok.cicerone.android

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.SystemMessage

/**
 * Implementation of [Navigator] based on support fragments.
 *
 * [BackTo] navigation command will return to root if needed screen isn't found in the screens
 * chain. To change this behavior override [backToUnexisting] method.
 *
 * [Back] command will call [exit] method if current screen is root.
 *
 * @param fragmentManager support fragment manager
 * @param containerId     id of the fragments container layout
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 * @see FragmentNavigator
 */
abstract class SupportFragmentNavigator(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
) : Navigator {

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
            command: Command,
            currentFragment: Fragment,
            nextFragment: Fragment,
            fragmentTransaction: FragmentTransaction
    ) {
        // Do nothing by default
    }

    override fun applyCommand(command: Command) {
        if (command is Forward) {
            val fragment = createFragment(command.screenKey, command.transitionData)
            if (fragment == null) {
                unknownScreen(command)
                return
            }

            val fragmentTransaction = fragmentManager.beginTransaction()
            setupFragmentTransactionAnimation(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            )

            fragmentTransaction
                    .replace(containerId, fragment)
                    .addToBackStack(command.screenKey)
                    .commit()
        } else if (command is Back) {
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStackImmediate()
            } else {
                exit()
            }
        } else if (command is Replace) {
            val fragment = createFragment(command.screenKey, command.transitionData)
            if (fragment == null) {
                unknownScreen(command)
                return
            }
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStackImmediate()

                val fragmentTransaction = fragmentManager.beginTransaction()
                setupFragmentTransactionAnimation(
                        command = command,
                        currentFragment = fragmentManager.findFragmentById(containerId),
                        nextFragment = fragment,
                        fragmentTransaction = fragmentTransaction
                )

                fragmentTransaction
                        .replace(containerId, fragment)
                        .addToBackStack(command.screenKey)
                        .commit()
            } else {
                val fragmentTransaction = fragmentManager.beginTransaction()
                setupFragmentTransactionAnimation(
                        command,
                        fragmentManager.findFragmentById(containerId),
                        fragment,
                        fragmentTransaction
                )

                fragmentTransaction
                        .replace(containerId, fragment)
                        .commit()
            }
        } else if (command is BackTo) {
            val key = command.screenKey

            if (key == null) {
                backToRoot()
            } else {
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
            }
        } else if (command is SystemMessage) {
            showSystemMessage(command.message)
        }
    }

    private fun backToRoot() {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
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

    /**
     * Called when we tried to back to some specific screen, but didn't found it.
     */
    protected open fun backToUnexisting() {
        backToRoot()
    }

    /**
     * Called if we can't create a screen.
     */
    protected open fun unknownScreen(command: Command) {
        throw RuntimeException("Can't create a screen for passed screenKey.")
    }
}
