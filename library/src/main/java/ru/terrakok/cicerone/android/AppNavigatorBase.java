package ru.terrakok.cicerone.android;


import android.content.Intent;
import android.widget.Toast;

import ru.terrakok.cicerone.android.adapters.ActivityAdapter;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Allow open new or replace current activity.
 * <p>
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 * </p>
 *
 * @author Vasili Chyrvon (vasili.chyrvon@gmail.com)
 */
abstract class AppNavigatorBase extends NavigatorBase {
    private final ActivityAdapter activity;

    protected AppNavigatorBase(ActivityAdapter activity) {
        this.activity = activity;
    }

    /**
     * Opens new screen based on the passed command.
     *
     * @param forward forward command to apply
     */
    @Override
    protected void applyForward(Forward forward) {
        Intent activityIntent = createActivityIntent(forward.getScreenKey(), forward.getTransitionData());

        // Start activity
        if (activityIntent != null) {
            activity.startActivity(activityIntent);
            return;
        }
    }

    /**
     * Rolls back the last transition from the screens chain based on the passed command.
     *
     * @param back back command to apply
     */
    @Override
    protected void applyBack(Back back) {

    }

    /**
     * Replaces the current screen based on the passed command.
     *
     * @param replace replace command to apply
     */
    @Override
    protected void applyReplace(Replace replace) {
        Intent activityIntent = createActivityIntent(replace.getScreenKey(), replace.getTransitionData());

        // Replace activity
        if (activityIntent != null) {
            activity.startActivity(activityIntent);
            activity.finish();
            return;
        }
    }

    /**
     * Rolls back to the needed screen from the screens chain based on the passed command.
     *
     * @param backTo backTo command to apply
     */
    @Override
    protected void applyBackTo(BackTo backTo) {

    }

    /**
     * Called when we tried to back to some specific screen, but didn't found it.
     */
    @Override
    protected void backToUnexisting() {

    }

    /**
     * Creates Intent to start Activity for {@code screenKey}.
     * <p>
     * <b>Warning:</b> This method does not work with {@link BackTo} command.
     * </p>
     *
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return intent to start Activity for the passed screen key
     */
    protected abstract Intent createActivityIntent(String screenKey, Object data);

    /**
     * Shows system message.
     *
     * @param message message to show
     */
    @Override
    protected void showSystemMessage(String message) {
        // Toast by default
        Toast.makeText(activity.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when we try to back from the root.
     */
    @Override
    protected void exit() {
        // Finish by default
        activity.finish();
    }
}
