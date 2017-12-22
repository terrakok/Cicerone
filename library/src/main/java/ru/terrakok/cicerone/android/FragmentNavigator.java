package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

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
    @NonNull
    private FragmentManager fragmentManager;
    private int containerId;

    /**
     * Creates FragmentNavigator.
     *
     * @param fragmentManager fragment manager
     * @param containerId     id of the fragments container layout
     */
    public FragmentNavigator(@NonNull FragmentManager fragmentManager, int containerId) {
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
    protected void setupFragmentTransactionAnimation(@NonNull Command command,
                                                     @NonNull Fragment currentFragment,
                                                     @NonNull Fragment nextFragment,
                                                     @NonNull FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void applyCommand(@NonNull Command command) {
        if (command instanceof Forward) {
            Forward forward = (Forward) command;
            Fragment fragment = createFragment(forward.getScreenKey(), forward.getTransitionData());
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
                    .addToBackStack(forward.getScreenKey())
                    .commit();
        } else if (command instanceof Back) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            } else {
                exit();
            }
        } else if (command instanceof Replace) {
            Replace replace = (Replace) command;
            Fragment fragment = createFragment(replace.getScreenKey(), replace.getTransitionData());
            if (fragment == null) {
                unknownScreen(command);
                return;
            }
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                setupFragmentTransactionAnimation(
                        command,
                        fragmentManager.findFragmentById(containerId),
                        fragment,
                        fragmentTransaction
                );

                fragmentTransaction
                        .replace(containerId, fragment)
                        .addToBackStack(replace.getScreenKey())
                        .commit();
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
        } else if (command instanceof BackTo) {
            String key = ((BackTo) command).getScreenKey();

            if (key == null) {
                backToRoot();
            } else {
                boolean hasScreen = false;
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    if (key.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                        fragmentManager.popBackStackImmediate(key, 0);
                        hasScreen = true;
                        break;
                    }
                }
                if (!hasScreen) {
                    backToUnexisting();
                }
            }
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    private void backToRoot() {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     * <p>
     * If it returns null, will be called {@link #unknownScreen(Command)}.
     * </p>
     *
     * @param screenKey screen key
     * @param data      initialization data
     * @return instantiated fragment for the passed screen key, or null if there no fragment that
     *         accords to passed screenKey
     */
    @Nullable
    protected abstract Fragment createFragment(@NonNull String screenKey, @Nullable Object data);

    /**
     * Shows system message.
     *
     * @param message message to show
     */
    protected abstract void showSystemMessage(@NonNull String message);

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
    protected void unknownScreen(@NonNull Command command) {
        throw new RuntimeException("Can't create a screen for passed screenKey.");
    }
}
