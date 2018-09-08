/*
 * Created by Vasili Chyrvon (vasili.chyrvon@gmail.com)
 */

package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Extends {@link SupportFragmentNavigator} to allow
 * open new or replace current activity.
 * <p>
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 * </p>
 */
public abstract class SupportAppNavigator extends SupportFragmentNavigator {

    private Activity activity;

    public SupportAppNavigator(FragmentActivity activity, int containerId) {
        super(activity.getSupportFragmentManager(), containerId);
        this.activity = activity;
    }

    public SupportAppNavigator(FragmentActivity activity, FragmentManager fragmentManager, int containerId) {
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
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
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
        SupportAppScreen screen = (SupportAppScreen) command.getScreen();
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

    private void checkAndStartActivity(SupportAppScreen screen, Intent activityIntent, Bundle options) {
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
     * @param screen screen key
     * @param activityIntent intent passed to start Activity for the {@code screenKey}
     */
    protected void unexistingActivity(SupportAppScreen screen, Intent activityIntent) {
        // Do nothing by default
    }

    @Override
    protected void exit() {
        // Finish by default
        activity.finish();
    }
}