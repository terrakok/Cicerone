package ru.terrakok.cicerone.sample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity;
import ru.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment;
import ru.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment;
import ru.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity;
import ru.terrakok.cicerone.sample.ui.bottom.ForwardFragment;
import ru.terrakok.cicerone.sample.ui.bottom.TabContainerFragment;
import ru.terrakok.cicerone.sample.ui.main.MainActivity;
import ru.terrakok.cicerone.sample.ui.main.SampleFragment;
import ru.terrakok.cicerone.sample.ui.start.StartActivity;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class Screens {
    public static final class SampleScreen extends SupportAppScreen {
        private final int number;

        public SampleScreen(int number) {
            this.number = number;
            this.screenKey = getClass().getSimpleName() + "_" + number;
        }

        @Override
        public Fragment getFragment() {
            return SampleFragment.getNewInstance(number);
        }
    }

    public static final class StartScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, StartActivity.class);
        }
    }

    public static final class MainScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, MainActivity.class);
        }
    }

    public static final class BottomNavigationScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, BottomNavigationActivity.class);
        }
    }

    public static final class TabScreen extends SupportAppScreen {
        private final String tabName;

        public TabScreen(String tabName) {
            this.tabName = tabName;
        }

        @Override
        public Fragment getFragment() {
            return TabContainerFragment.getNewInstance(tabName);
        }
    }

    public static final class ForwardScreen extends SupportAppScreen {
        private final String containerName;
        private final int number;

        public ForwardScreen(String containerName, int number) {
            this.containerName = containerName;
            this.number = number;
        }

        @Override
        public Fragment getFragment() {
            return ForwardFragment.getNewInstance(containerName, number);
        }
    }

    public static final class GithubScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"));
        }
    }

    public static final class ProfileScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, ProfileActivity.class);
        }
    }

    public static final class ProfileInfoScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new ProfileFragment();
        }
    }

    public static final class SelectPhotoScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new SelectPhotoFragment();
        }
    }
}
