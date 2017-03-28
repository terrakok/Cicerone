package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.app.FragmentManager;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;

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
    private FragmentManager fragmentManager;
    private int containerId;

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

    @Override
    public void applyReplace(String screenKey, Object transitionData) {
        Fragment fragment = createFragment(screenKey, transitionData);
        if (fragment == null) {
            unknownScreen();
            return;
        }
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(screenKey)
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .commit();
        }
    }

    @Override
    public void applyForward(String screenKey, Object transitionData) {
        Fragment fragment = createFragment(screenKey, transitionData);
        if (fragment == null) {
            unknownScreen();
            return;
        }
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(screenKey)
                .commit();
    }

    @Override
    public void applyBack() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            exit();
        }
    }

    @Override
    public void applyBackTo(String key) {
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
    }

    @Override
    public void applySystemMessage(String message) {
        showSystemMessage(message);
    }

    private void backToRoot() {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        fragmentManager.executePendingTransactions();
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
    protected void unknownScreen() {
        throw new RuntimeException("Can't create a screen for passed screenKey.");
    }
}