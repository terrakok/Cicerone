package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import ru.terrakok.cicerone.android.adapters.ActivityAdapter;

/**
 * Allow open new or replace current activity.
 * <p>
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 * </p>
 *
 * @author Vasili Chyrvon (vasili.chyrvon@gmail.com)
 */
public abstract class SupportAppNavigator extends AppNavigatorBase {

    public SupportAppNavigator(FragmentActivity activity, @Deprecated int containerId) {
        super(new SupportActivityAdapter(activity));
    }

    @Deprecated
    public SupportAppNavigator(FragmentActivity activity, @Deprecated FragmentManager fragmentManager, @Deprecated int containerId) {
        this(activity, containerId);
    }

    /**
     * Creates Fragment matching {@code screenKey}.
     *
     * @param screenKey screen key
     * @param data      initialization data
     * @return instantiated fragment for the passed screen key
     */
    @Deprecated
    protected abstract Fragment createFragment(String screenKey, Object data);

    private static class SupportActivityAdapter implements ActivityAdapter {
        private final Activity activity;

        public SupportActivityAdapter(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void startActivity(Intent activityIntent) {
            activity.startActivity(activityIntent);
        }

        @Override
        public void finish() {
            activity.finish();
        }

        @Override
        public Context getContext() {
            return activity;
        }
    }
}