package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import ru.terrakok.cicerone.commands.BackTo;
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
 *
 * @author Vasili Chyrvon (vasili.chyrvon@gmail.com)
 */
public abstract class SupportAppNavigator extends SupportFragmentNavigator {

    @NonNull
    private Activity activity;

    public SupportAppNavigator(@NonNull FragmentActivity activity, int containerId) {
        super(activity.getSupportFragmentManager(), containerId);
        this.activity = activity;
    }

    public SupportAppNavigator(@NonNull FragmentActivity activity,
                               @NonNull FragmentManager fragmentManager,
                               int containerId) {
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
    @Nullable
    protected Bundle createStartActivityOptions(@NonNull Command command,
                                                @NonNull Intent activityIntent) {
        return null;
    }

    @Override
    public void applyCommand(@NonNull Command command) {
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

    private void checkAndStartActivity(@NonNull String screenKey,
                                       @NonNull Intent activityIntent,
                                       @Nullable Bundle options) {
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
    protected void unexistingActivity(@NonNull String screenKey, @NonNull Intent activityIntent) {
        // Do nothing by default
    }

    /**
     * Creates Intent to start Activity for {@code screenKey}.
     * <p>
     * If it returns null, screenKey will be passed to {@link #createFragment(String, Object)}.
     * <b>Warning:</b> This method does not work with {@link BackTo} command.
     * </p>
     *
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return intent to start Activity for the passed screen key, or null if there no activity
     *         that accords to passed screenKey
     */
    @Nullable
    protected abstract Intent createActivityIntent(@NonNull Context context,
                                                   @NonNull String screenKey,
                                                   @Nullable Object data);

    @Override
    protected void showSystemMessage(@NonNull String message) {
        // Toast by default
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void exit() {
        // Finish by default
        activity.finish();
    }
}
