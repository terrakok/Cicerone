package ru.terrakok.cicerone.android.aac;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class AacNavigator implements Navigator {

    private final NavController navController;
    private final FragmentActivity activity;

    public AacNavigator(FragmentActivity activity, int navFragmentId) {
        this.activity = activity;
        Fragment navFrag = activity.getSupportFragmentManager().findFragmentById(navFragmentId);
        if (navFrag instanceof NavHostFragment) {
            navController = ((NavHostFragment) navFrag).getNavController();
        } else {
            throw new IllegalArgumentException("No NavHostFragment found at given FragmentActivity." +
                    " Please make sure you don't forget to add it!");
        }
    }

    @Override
    public void applyCommands(Command[] commands) {
        for (Command command : commands) {
            applyCommand(command);
        }
    }

    protected void finishActivity() {
        activity.finish();
    }

    private void applyCommand(Command command) {
        if (command instanceof Forward) {
            fragmentForward(((Forward) command).getScreen());
        } else if (command instanceof Replace) {
            fragmentReplace(((Replace) command).getScreen());
        } else if (command instanceof BackTo) {
            fragmentBackTo(((BackTo) command).getScreen());
        } else if (command instanceof Back) {
            fragmentBack();
        }
    }

    private void fragmentReplace(Screen screen) {
        fragmentBack();
        fragmentForward(screen);
    }

    private void fragmentForward(Screen screen) {
        AacScreen aacScreen = checkScreenInstance(screen);
        navController.navigate(
                aacScreen.getNavigationResId(),
                aacScreen.getArgs(),
                aacScreen.getNavOptions(),
                aacScreen.getNavExtras());
    }

    private void fragmentBack() {
        // if we haven't popped fragment it means that no fragments left and we have to finish an activity
        if (!navController.popBackStack()) {
            finishActivity();
        }
    }

    private void fragmentBackTo(Screen screen) {
        AacScreen aacScreen = checkScreenInstance(screen);
        navController.popBackStack(aacScreen.getNavigationResId(), false);
    }

    private AacScreen checkScreenInstance(Screen screen) {
        if (screen instanceof AacScreen) {
            return (AacScreen) screen;
        }
        throw new IllegalArgumentException("Screen should be instance of AacScreen!");
    }
}
