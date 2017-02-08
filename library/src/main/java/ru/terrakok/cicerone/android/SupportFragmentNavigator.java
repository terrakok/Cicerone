package ru.terrakok.cicerone.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
 * {@link Navigator} implementation based on the support fragments.
 * <p>
 * {@link BackTo} navigation command will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override {@link #backToUnexisting()} method.
 * </p>
 * <p>
 * {@link Back} command will call {@link #exit()} method if current screen is the root.
 * </p>
 */
public abstract class SupportFragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;
    private int[] animations = { 0, 0, 0, 0 };
    private boolean animationExists;
    private boolean popAnimationExists;

    /**
     * Creates SupportFragmentNavigator.
     * @param fragmentManager support fragment manager
     * @param containerId id of the fragments container layout
     */
    public SupportFragmentNavigator(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    /**
     * Set specific animation resources to run for the fragments that are replacing by this navigator.
     * These animations will not be played when popping the back stack.
     */
    public void setCustomAnimations(int enter, int exit) {
        animations[0] = enter;
        animations[1] = exit;
        animationExists = true;
    }

    /**
     * Set specific animation resources to run for the fragments that are replacing by this navigator.
     * The <code>popEnter</code> and <code>popExit</code> animations will be played for enter/exit
     * operations specifically when popping the back stack.
     */
    public void setCustomAnimations(int enter, int exit, int popEnter, int popExit) {
        setCustomAnimations(enter, exit);
        animations[2] = popEnter;
        animations[3] = popExit;
        popAnimationExists = true;
    }

    private FragmentTransaction createTransaction() {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (popAnimationExists) {
            transaction.setCustomAnimations(animations[0], animations[1], animations[2], animations[3]);
        } else if (animationExists) {
            transaction.setCustomAnimations(animations[0], animations[1]);
        }
        return transaction;
    }

    @Override
    public void applyCommand(Command command) {
        if (command instanceof Forward) {
            Forward forward = (Forward) command;
            createTransaction()
                    .replace(containerId, createFragment(forward.getScreenKey(), forward.getTransitionData()))
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
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
                createTransaction()
                        .replace(containerId, createFragment(replace.getScreenKey(), replace.getTransitionData()))
                        .addToBackStack(replace.getScreenKey())
                        .commit();
            } else {
                createTransaction()
                        .replace(containerId, createFragment(replace.getScreenKey(), replace.getTransitionData()))
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
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        fragmentManager.executePendingTransactions();
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     * @param screenKey screen key
     * @param data initialization data
     * @return instantiated fragment for the passed screen key
     */
    protected abstract Fragment createFragment(String screenKey, Object data);

    /**
     * Shows system message.
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
}
