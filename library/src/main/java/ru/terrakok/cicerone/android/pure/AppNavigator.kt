package ru.terrakok.cicerone.android.pure

import android.app.Activity
import android.app.Fragment
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import java.util.*

open class AppNavigator(
    protected val activity: Activity,
    protected val fragmentManager: FragmentManager,
    protected val containerId: Int
) : Navigator {

    protected var localStackCopy: LinkedList<String>? = null

    constructor(
        activity: Activity,
        containerId: Int
    ) : this(activity, activity.fragmentManager, containerId)

    override fun applyCommands(commands: Array<out Command>) {
        fragmentManager.executePendingTransactions()

        //copy stack before apply commands
        copyStackToLocal()
        commands.forEach { command ->
            try {
                applyCommand(command)
            } catch (e: RuntimeException) {
                errorOnApplyCommand(command, e)
            }
        }
    }

    private fun copyStackToLocal() {
        localStackCopy = LinkedList()
        val stackSize = fragmentManager.backStackEntryCount
        for (i in 0 until stackSize) {
            localStackCopy!!.add(fragmentManager.getBackStackEntryAt(i).name)
        }
    }

    protected open fun applyCommand(command: Command) {
        when (command) {
            is Forward -> activityForward(command)
            is Replace -> activityReplace(command)
            is BackTo -> backTo(command)
            is Back -> fragmentBack()
        }
    }


    protected open fun activityForward(command: Forward) {
        val screen = command.getScreen() as AppScreen
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
        val screen = command.getScreen() as AppScreen
        val fragment = createFragment(screen)
        val fragmentTransaction = fragmentManager.beginTransaction()
        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )
        fragmentTransaction
            .replace(containerId, fragment)
            .addToBackStack(screen.screenKey)
            .commit()
        localStackCopy!!.add(screen.screenKey)
    }

    protected open fun fragmentBack() {
        if (localStackCopy!!.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy!!.removeLast()
        } else {
            activityBack()
        }
    }

    protected open fun activityBack() {
        activity.finish()
    }

    protected open fun activityReplace(command: Replace) {
        val screen = command.getScreen() as AppScreen
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
        val screen =
            command.getScreen() as AppScreen
        val fragment = createFragment(screen)
        if (localStackCopy!!.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy!!.removeLast()
            val fragmentTransaction =
                fragmentManager.beginTransaction()
            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
            )
            fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(screen.screenKey)
                .commit()
            localStackCopy!!.add(screen.screenKey)
        } else {
            val fragmentTransaction =
                fragmentManager.beginTransaction()
            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
            )
            fragmentTransaction
                .replace(containerId, fragment)
                .commit()
        }
    }

    protected open fun backTo(command: BackTo) {
        if (command.getScreen() == null) {
            backToRoot()
        } else {
            val key: String = command.getScreen()!!.screenKey
            val index = localStackCopy!!.indexOf(key)
            val size = localStackCopy!!.size
            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy!!.removeLast()
                }
                fragmentManager.popBackStack(key, 0)
            } else {
                backToUnexisting((command.getScreen() as AppScreen))
            }
        }
    }

    private fun backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy!!.clear()
    }

    protected open fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {}

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

    protected open fun unexistingActivity(
        screen: AppScreen,
        activityIntent: Intent
    ) {
        // Do nothing by default //
    }

    protected fun createFragment(screen: AppScreen): Fragment? {
        val fragment = screen.getFragment()
        if (fragment == null) {
            errorWhileCreatingScreen(screen)
            throw RuntimeException("Can't create a screen: " + screen.screenKey)
        }
        return fragment
    }

    protected open fun backToUnexisting(screen: AppScreen) {
        backToRoot()
    }

    protected open fun errorWhileCreatingScreen(screen: AppScreen) {
        // Do nothing by default
    }

    protected open fun errorOnApplyCommand(
        command: Command,
        error: RuntimeException
    ): Unit = throw error
}