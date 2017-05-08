package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.app.FragmentManager;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

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
public abstract class FragmentNavigator extends NavigatorBase {
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

    /**
     * Opens new screen based on the passed command.
     *
     * @param forward forward command to apply
     */
    @Override
    protected void applyForward(Forward forward) {
        Fragment fragment = createFragment(forward.getScreenKey(), forward.getTransitionData());
        if (fragment == null) {
            unknownScreen(forward);
            return;
        }
        fragmentManager
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(forward.getScreenKey())
                .commit();
    }

    /**
     * Rolls back the last transition from the screens chain based on the passed command.
     *
     * @param back back command to apply
     */
    @Override
    protected void applyBack(Back back) {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            exit();
        }
    }

    /**
     * Replaces the current screen based on the passed command.
     *
     * @param replace replace command to apply
     */
    @Override
    protected void applyReplace(Replace replace) {
        Fragment fragment = createFragment(replace.getScreenKey(), replace.getTransitionData());
        if (fragment == null) {
            unknownScreen(replace);
            return;
        }
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .addToBackStack(replace.getScreenKey())
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, fragment)
                    .commit();
        }
    }

    /**
     * Rolls back to the needed screen from the screens chain based on the passed command.
     *
     * @param backTo backTo command to apply
     */
    @Override
    protected void applyBackTo(BackTo backTo) {
        String key = backTo.getScreenKey();

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
     * Called when we tried to back to some specific screen, but didn't found it.
     */
    @Override
    protected void backToUnexisting() {
        backToRoot();
    }

}