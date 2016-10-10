package ru.terrakok.cicerone.android;

import android.app.Fragment;
import android.app.FragmentManager;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

/**
 * Created by terrakok on 03.10.16.
 */

public abstract class FragmentNavigator implements Navigator {
    private FragmentManager fragmentManager;
    private int containerId;

    public FragmentNavigator(FragmentManager fragmentManager, int containerId) {
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
    }

    @Override
    public void applyCommand(Command command) {
        if (command instanceof Forward) {
            fragmentManager
                    .beginTransaction()
                    .replace(containerId, createFragment(((Forward) command).getScreenKey(), ((Forward) command).getTransitionData()))
                    .addToBackStack(((Forward) command).getScreenKey())
                    .commit();
        } else if (command instanceof Back) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
            } else exit();
        } else if (command instanceof Replace) {
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStackImmediate();
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(((Replace) command).getScreenKey(), ((Replace) command).getTransitionData()))
                        .addToBackStack(((Replace) command).getScreenKey())
                        .commit();
            } else {
                fragmentManager
                        .beginTransaction()
                        .replace(containerId, createFragment(((Replace) command).getScreenKey(), ((Replace) command).getTransitionData()))
                        .commit();
            }
        } else if (command instanceof BackTo) {
            String key = ((BackTo) command).getScreenKey();

            boolean hasScreen = false;
            if (key != null) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    if (key.equals(fragmentManager.getBackStackEntryAt(i).getName())) {
                        fragmentManager.popBackStackImmediate(key, 0);
                        hasScreen = true;
                        break;
                    }
                }
            }

            if (!hasScreen) {
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                    fragmentManager.popBackStack();
                }
                fragmentManager.executePendingTransactions();
            }
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    protected abstract Fragment createFragment(String screenKey, Object data);

    protected abstract void showSystemMessage(String message);

    protected abstract void exit();
}
