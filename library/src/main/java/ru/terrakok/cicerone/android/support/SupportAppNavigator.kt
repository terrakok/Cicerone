package ru.terrakok.cicerone.android.support

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace
import java.util.*

open class SupportAppNavigator(
   protected val activity: FragmentActivity,
   protected val fragmentManager: FragmentManager,
   protected val containerId: Int
) : Navigator {

    protected var localStackCopy: LinkedList<String>? = null

    constructor(
        activity: FragmentActivity,
        containerId: Int
    ) : this(activity, activity.supportFragmentManager, containerId)

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
        val screen = command.getScreen() as SupportAppScreen
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
        val screen = command.getScreen() as SupportAppScreen
        val fragmentParams = screen.getFragmentParams()
        val fragment = if (fragmentParams == null) createFragment(screen) else null
        val fragmentTransaction = fragmentManager.beginTransaction()
        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )
        if (fragmentParams != null) {
            fragmentTransaction.replace(
                containerId,
                fragmentParams.getFragmentClass(),
                fragmentParams.getArguments()
            )
        } else {
            fragmentTransaction.replace(containerId, fragment)
        }
        fragmentTransaction
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
        val screen =
            command.getScreen() as SupportAppScreen
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
        val screen = command.getScreen() as SupportAppScreen
        val fragmentParams = screen.getFragmentParams()
        val fragment =
            if (fragmentParams == null) createFragment(screen) else null
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
            if (fragmentParams != null) {
                fragmentTransaction.replace(
                    containerId,
                    fragmentParams.getFragmentClass(),
                    fragmentParams.getArguments()
                )
            } else {
                fragmentTransaction.replace(containerId, fragment)
            }
            fragmentTransaction
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
                backToUnexisting(command.getScreen() as SupportAppScreen)
            }
        }
    }

    private fun backToRoot() {
        fragmentManager.popBackStack(
            null,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
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
        screen: SupportAppScreen,
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
        screen: SupportAppScreen,
        activityIntent: Intent
    ) {
        // Do nothing by default
    }

    protected open fun createFragment(screen: SupportAppScreen): Fragment? {
        val fragment = screen.getFragment()
        if (fragment == null) {
            errorWhileCreatingScreen(screen)
            throw RuntimeException("Can't create a screen: " + screen.screenKey)
        }
        return fragment
    }

    protected open fun backToUnexisting(screen: SupportAppScreen) {
        backToRoot()
    }

    protected open fun errorWhileCreatingScreen(screen: SupportAppScreen) {
        // Do nothing by default
    }

    protected open fun errorOnApplyCommand(
        command: Command,
        error: RuntimeException
    ): Nothing = throw error
}