/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.LinkedList;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

/**
 * {@link Navigator} implementation based on the fragments.
 * <p>
 * {@link BackTo} navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override {@link #backToUnexisting()} method.
 * </p>
 * <p>
 * {@link Back} command will call {@link #exit()} method if current screen is the root.
 * </p>
 */
public abstract class FragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;
    private LinkedList<String> localStackCopy;

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
     * Override this method to setup custom fragment transaction animation.
     *
     * @param command             current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param currentFragment     current fragment in container
     *                            (for {@link Replace} command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    protected void setupFragmentTransactionAnimation(Command command,
                                                     Fragment currentFragment,
                                                     Fragment nextFragment,
                                                     FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void applyCommands(Command[] commands) {
        fragmentManager.executePendingTransactions();

        //copy stack structure before apply commands
        final int stackSize = fragmentManager.getBackStackEntryCount();
        localStackCopy = new LinkedList<>();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.add(fragmentManager.getBackStackEntryAt(i).getName());
        }

        for (Command command : commands) applyCommand(command);
    }

    protected void applyCommand(Command command) {
        if (command instanceof Forward) {
            forward((Forward) command);
        } else if (command instanceof Back) {
            back();
        } else if (command instanceof Replace) {
            replace((Replace) command);
        } else if (command instanceof BackTo) {
            backTo((BackTo) command);
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    protected void forward(Forward command) {
        Fragment fragment = createFragment(command.getScreenKey(), command.getTransitionData());
        if (fragment == null) {
            unknownScreen(command);
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        setupFragmentTransactionAnimation(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
        );

        fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(command.getScreenKey())
                .commit();
        localStackCopy.add(command.getScreenKey());
    }

    protected void back() {
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.pop();
        } else {
            exit();
        }
    }

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
            setupFragmentTransactionAnimation(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment)
                    .addToBackStack(command.getScreenKey())
                    .commit();
            localStackCopy.add(command.getScreenKey());
        } else {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            setupFragmentTransactionAnimation(
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

    protected void backTo(BackTo command) {
        String key = command.getScreenKey();

        if (key == null) {
            backToRoot();
        } else {
            int i = localStackCopy.indexOf(key);
            int s = localStackCopy.size();
            if (i != -1) {
                for (int j = 1; j < s - i; j++) {
                    localStackCopy.pop();
                }
                fragmentManager.popBackStack(key, 0);
            } else {
                backToUnexisting();
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
     * Shows system message.
     *
     * @param message message to show
     */
    protected abstract void showSystemMessage(String message);

    /**
     * Called when we try to back from the root.
     */
    protected abstract void exit();

    /**
     * Called when we tried to back to some specific screen, but didn't found it.
     */
    protected void backToUnexisting() {
        backToRoot();
    }


    /**
     * Called if we can't create a screen.
     */
    protected void unknownScreen(Command command) {
        throw new RuntimeException("Can't create a screen for passed screenKey.");
    }
}
