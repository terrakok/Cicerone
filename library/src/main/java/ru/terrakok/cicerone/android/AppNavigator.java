package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;

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
public abstract class AppNavigator extends AppNavigatorBase {

    public AppNavigator(Activity activity, @Deprecated int containerId) {
        super(new AndroidActivityAdapter(activity));
    }

    @Deprecated
    public AppNavigator(Activity activity, @Deprecated FragmentManager fragmentManager, @Deprecated int containerId) {
        this(activity, containerId);
    }

    private static class AndroidActivityAdapter implements ActivityAdapter {
        private final Activity activity;

        public AndroidActivityAdapter(Activity activity) {
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