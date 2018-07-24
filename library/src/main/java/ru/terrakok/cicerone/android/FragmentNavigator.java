/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.ArrayDeque;
import java.util.Iterator;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * {@link Navigator} implementation based on the fragments.
 * <p>
 * {@link BackTo} navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override {@link #backToUnexisting(String)} method.
 * </p>
 * <p>
 * {@link Back} command will call {@link #exit()} method if current screen is the root.
 * </p>
 */
public abstract class FragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;
    private ArrayDeque<String> localStackCopy;

    /**
     * Creates FragmentNavigator.
     *
     * @param fragmentManager fragment manager
     * @param containerId     id of the fragments container layout
     */
    public FragmentNavigator(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    /**
     * Override this method to setup fragment transaction {@link FragmentTransaction}.
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param command             current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param currentFragment     current fragment in container
     *                            (for {@link Replace} command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    protected void setupFragmentTransaction(Command command,
                                                     Fragment currentFragment,
                                                     Fragment nextFragment,
                                                     FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void applyCommands(Command[] commands) {
        fragmentManager.executePendingTransactions();

        //copy stack before apply commands
        copyStackToLocal();

        for (Command command : commands) {
            applyCommand(command);
        }
    }

    private void copyStackToLocal() {
        localStackCopy = new ArrayDeque<>();

        final int stackSize = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.push(fragmentManager.getBackStackEntryAt(i).getName());
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected void applyCommand(Command command) {
        if (command instanceof Forward) {
            forward((Forward) command);
        } else if (command instanceof Back) {
            back();
        } else if (command instanceof Replace) {
            replace((Replace) command);
        } else if (command instanceof BackTo) {
            backTo((BackTo) command);
        }
    }

    /**
     * Performs {@link Forward} command transition
     */
    protected void forward(Forward command) {
        Fragment fragment = createFragment(command.getScreenKey(), command.getTransitionData());

        if (fragment == null) {
            unknownScreen(command);
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
        );

        fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(command.getScreenKey())
                .commit();
        localStackCopy.push(command.getScreenKey());
    }

    /**
     * Performs {@link Forward} command transition
     */
    protected void back() {
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.pop();
        } else {
            exit();
        }
    }

    /**
     * Performs {@link Replace} command transition
     */
    protected void replace(Replace command) {
        Fragment fragment = createFragment(command.getScreenKey(), command.getTransitionData());

        if (fragment == null) {
            unknownScreen(command);
            return;
        }

        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.pop();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            setupFragmentTransaction(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment)
                    .addToBackStack(command.getScreenKey())
                    .commit();
            localStackCopy.push(command.getScreenKey());

        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            setupFragmentTransaction(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment)
                    .commit();
        }
    }

    /**
     * Performs {@link BackTo} command transition
     */
    protected void backTo(BackTo command) {
        String key = command.getScreenKey();

        if (key == null) {
            backToRoot();

        } else {
            if (localStackCopy.contains(key)) {
                Iterator<String> stackIterator = localStackCopy.iterator();
                while (!key.equals(stackIterator.next())) {
                    localStackCopy.pop();
                }
                fragmentManager.popBackStack(key, 0);
            } else {
                backToUnexisting(command.getScreenKey());
            }
        }
    }

    private void backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        localStackCopy.clear();
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     *
     * @param screenKey screen key
     * @param data      initialization data
     * @return instantiated fragment for the passed screen key
     */
    protected abstract Fragment createFragment(String screenKey, Object data);

    /**
     * Called when we try to back from the root.
     */
    protected abstract void exit();

    /**
     * Called when we tried to back to some specific screen (via {@link BackTo} command),
     * but didn't found it.
     * @param screenKey screen key
     */
    protected void backToUnexisting(String screenKey) {
        backToRoot();
    }


    /**
     * Called if we can't create a screen.
     */
    protected void unknownScreen(Command command) {
        throw new RuntimeException("Can't create a screen for passed screenKey.");
    }
}
