package ru.terrakok.cicerone.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.CommandType;
import ru.terrakok.cicerone.commands.SystemMessage;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * {@link Navigator} implementation based on the support fragments.
 * <p>
 * {@link Command} with type CommandType.TYPE_BACK_TO  will return to the root if
 * needed screen isn't found in the screens chain.
 * To change this behavior override {@link #backToUnexisting()} method.
 * </p>
 * <p>
 * {@link Command} with type CommandType.TYPE_BACK will call {@link #exit()} method if current screen is the root.
 * </p>
 */
public abstract class SupportFragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;

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

    @Override
    public void applyCommand(Command command) {
        String screenKey = command.getScreenKey();
        Object transitionData = command.getTransitionData();
        switch (command.getCommandType()) {
            case CommandType.TYPE_FORWARD:
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(screenKey, transitionData))
                        .addToBackStack(screenKey)
                        .commit();
                break;
            case CommandType.TYPE_BACK:
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                } else {
                    exit();
                }
                break;
            case CommandType.TYPE_REPLACE:
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStackImmediate();
                    fragmentManager
                            .beginTransaction()
                            .replace(containerId, createFragment(screenKey, transitionData))
                            .addToBackStack(screenKey)
                            .commit();
                } else {
                    fragmentManager
                            .beginTransaction()
                            .replace(containerId, createFragment(screenKey, transitionData))
                            .commit();
                }
                break;
            case CommandType.TYPE_BACK_TO:

                if (screenKey == null) {
                    backToRoot();
                } else {
                    boolean hasScreen = false;
                    for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                        if (screenKey.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                            fragmentManager.popBackStackImmediate(screenKey, 0);
                            hasScreen = true;
                            break;
                        }
                    }
                    if (!hasScreen) {
                        backToUnexisting();
                    }
                }
                break;
            case CommandType.TYPE_SYSTEM_MESSAGE:
                showSystemMessage(((SystemMessage) command).getMessage());
                break;
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
}
