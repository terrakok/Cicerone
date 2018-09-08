/*
 * Created by Vasili Chyrvon (vasili.chyrvon@gmail.com)
 */

package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Extends {@link FragmentNavigator} to allow
 * open new or replace current activity.
 * <p>
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 * </p>
 */
public abstract class AppNavigator extends FragmentNavigator {

    private Activity activity;

    public AppNavigator(Activity activity, int containerId) {
        super(activity.getFragmentManager(), containerId);
        this.activity = activity;
    }

    public AppNavigator(Activity activity, FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
        this.activity = activity;
    }

    /**
     * Override this method to create option for start activity
     *
     * @param command        current navigation command. Will be only {@link Forward} or {@link Replace}
     * @param activityIntent activity intent
     * @return transition options
     */
    protected Bundle createStartActivityOptions(Command command, Intent activityIntent) {
        return null;
    }

    @Override
    protected void forward(Forward command) {
        AppScreen screen = (AppScreen) command.getScreen();
        Intent activityIntent = screen.getActivityIntent(activity);

        // Start activity
        if (activityIntent != null) {
            Bundle options = createStartActivityOptions(command, activityIntent);
            checkAndStartActivity(screen, activityIntent, options);
        } else {
            super.forward(command);
        }
    }

    @Override
    protected void replace(Replace command) {
        AppScreen screen = (AppScreen) command.getScreen();
        Intent activityIntent = screen.getActivityIntent(activity);

        // Replace activity
        if (activityIntent != null) {
            Bundle options = createStartActivityOptions(command, activityIntent);
            checkAndStartActivity(screen, activityIntent, options);
            activity.finish();
        } else {
            super.replace(command);
        }
    }

    private void checkAndStartActivity(AppScreen screen, Intent activityIntent, Bundle options) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(activityIntent, options);
        } else {
            unexistingActivity(screen, activityIntent);
        }
    }

    /**
     * Called when there is no activity to open {@code screenKey}.
     *
     * @param screen screen
     * @param activityIntent intent passed to start Activity for the {@code screenKey}
     */
    protected void unexistingActivity(AppScreen screen, Intent activityIntent) {
        // Do nothing by default
    }

    @Override
    protected void exit() {
        // Finish by default
        activity.finish();
    }
}