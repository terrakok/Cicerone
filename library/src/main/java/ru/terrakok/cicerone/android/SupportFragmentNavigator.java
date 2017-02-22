package ru.terrakok.cicerone.android;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

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
    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private int containerId;

    /**
     * Creates SupportFragmentNavigator.
     * @param activity {@link FragmentActivity}
     * @param containerId id of the fragments container layout
     */
    public SupportFragmentNavigator(FragmentActivity activity, int containerId) {
        this.activity = activity;
        this.fragmentManager = activity.getSupportFragmentManager();
        this.containerId = containerId;
    }

    @Override
    public void applyCommand(Command command) {
        if (command instanceof Forward) {
            Forward forward = (Forward) command;

            Intent activityIntent = createActivityIntent(forward.getScreenKey(), forward.getTransitionData());
            if (activityIntent != null) {
                activity.startActivity(activityIntent);
            } else {
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(forward.getScreenKey(), forward.getTransitionData()))
                        .addToBackStack(forward.getScreenKey())
                        .commit();
            }
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
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(replace.getScreenKey(), replace.getTransitionData()))
                        .addToBackStack(replace.getScreenKey())
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
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
     * Creates Activity Intent matching {@code screenKey}.<p/>
     * <b>Warning:</b> this method will be called only for {@link Forward} command!
     * It helps you start new Activity but don't create full featured Activity navigation.
     * @param screenKey screen key
     * @param data initialization data
     * @return Activity Intent for the passed screen key
     */
    protected Intent createActivityIntent(String screenKey, Object data) {
        return null;
    }

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
