/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.LinkedList;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * {@link Navigator} implementation based on the support fragments.
 * <p>
 * {@link BackTo} navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override {@link #backToUnexisting(SupportAppScreen)} method.
 * </p>
 * <p>
 * {@link Back} command will call {@link #exit()} method if current screen is the root.
 * </p>
 */
public abstract class SupportFragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;
    private LinkedList<String> localStackCopy;

    /**
     * Creates SupportFragmentNavigator.
     *
     * @param fragmentManager support fragment manager
     * @param containerId     id of the fragments container layout
     */
    public SupportFragmentNavigator(FragmentManager fragmentManager, int containerId) {
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
        localStackCopy = new LinkedList<>();

        final int stackSize = fragmentManager.getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            localStackCopy.add(fragmentManager.getBackStackEntryAt(i).getName());
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
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
        Fragment fragment = createFragment(screen);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction
        );

        fragmentTransaction
                .replace(containerId, fragment)
                .addToBackStack(screen.getScreenKey())
                .commit();
        localStackCopy.add(screen.getScreenKey());
    }

    /**
     * Performs {@link Back} command transition
     */
    protected void back() {
        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();
        } else {
            exit();
        }
    }

    /**
     * Performs {@link Replace} command transition
     */
    protected void replace(Replace command) {
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
        Fragment fragment = createFragment(screen);

        if (localStackCopy.size() > 0) {
            fragmentManager.popBackStack();
            localStackCopy.removeLast();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            setupFragmentTransaction(
                    command,
                    fragmentManager.findFragmentById(containerId),
                    fragment,
                    fragmentTransaction
            );

            fragmentTransaction
                    .replace(containerId, fragment)
                    .addToBackStack(screen.getScreenKey())
                    .commit();
            localStackCopy.add(screen.getScreenKey());

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
        String key = command.getScreen().getScreenKey();

        if (key == null) {
            backToRoot();

        } else {
            int index = localStackCopy.indexOf(key);
            int size = localStackCopy.size();

            if (index != -1) {
                for (int i = 1; i < size - index; i++) {
                    localStackCopy.removeLast();
                }
                fragmentManager.popBackStack(key, 0);
            } else {
                backToUnexisting((SupportAppScreen) command.getScreen());
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
     * @param screen screen
     * @return instantiated fragment for the passed screen
     */
    protected Fragment createFragment(SupportAppScreen screen) {
        Fragment fragment = screen.getFragment();

        if (fragment == null) {
            errorWhileCreatingFragment(screen);
        }
        return fragment;
    }

    /**
     * Called when we try to back from the root.
     */
    protected abstract void exit();

    /**
     * Called when we tried to back to some specific screen (via {@link BackTo} command),
     * but didn't found it.
     * @param screen screen
     */
    protected void backToUnexisting(SupportAppScreen screen) {
        backToRoot();
    }


    /**
     * Called if we can't create a fragment.
     */
    protected void errorWhileCreatingFragment(SupportAppScreen screen) {
        throw new RuntimeException("Can't create a fragment: " + screen.getClass().getSimpleName());
    }
}
