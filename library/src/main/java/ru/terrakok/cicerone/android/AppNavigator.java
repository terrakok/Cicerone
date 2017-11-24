package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ru.terrakok.cicerone.commands.BackTo;
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
 *
 * @author Vasili Chyrvon (vasili.chyrvon@gmail.com)
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
    public void applyCommand(Command command) {
        if (command instanceof Forward) {
            Forward forward = (Forward) command;
            Intent activityIntent = createActivityIntent(activity, forward.getScreenKey(), forward.getTransitionData());

            // Start activity
            if (activityIntent != null) {
                Bundle options = createStartActivityOptions(command, activityIntent);
                checkAndStartActivity(forward.getScreenKey(), activityIntent, options);
                return;
            }

        } else if (command instanceof Replace) {
            Replace replace = (Replace) command;
            Intent activityIntent = createActivityIntent(activity, replace.getScreenKey(), replace.getTransitionData());

            // Replace activity
            if (activityIntent != null) {
                Bundle options = createStartActivityOptions(command, activityIntent);
                checkAndStartActivity(replace.getScreenKey(), activityIntent, options);
                activity.finish();
                return;
            }
        }

        // Use default fragments navigation
        super.applyCommand(command);
    }

    private void checkAndStartActivity(String screenKey, Intent activityIntent, Bundle options) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(activityIntent, options);
        } else {
            unexistingActivity(screenKey, activityIntent);
        }
    }

    /**
     * Called when there is no activity to open {@code screenKey}.
     *
     * @param screenKey screen key
     * @param activityIntent intent passed to start Activity for the {@code screenKey}
     */
    protected void unexistingActivity(String screenKey, Intent activityIntent) {
        // Do nothing by default
    }

    /**
     * Creates Intent to start Activity for {@code screenKey}.
     * <p>
     * <b>Warning:</b> This method does not work with {@link BackTo} command.
     * </p>
     *
     *
     * @param context
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return intent to start Activity for the passed screen key
     */
    protected abstract Intent createActivityIntent(Context context, String screenKey, Object data);

    @Override
    protected void showSystemMessage(String message) {
        // Toast by default
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void exit() {
        // Finish by default
        activity.finish();
    }
}