package ru.terrakok.cicerone.sample;

import android.content.Context;
import android.content.Intent;

import ru.terrakok.cicerone.android.aac.AacScreen;
import ru.terrakok.cicerone.android.support.SupportAppScreen;
import ru.terrakok.cicerone.sample.ui.aac.AacActivity;

final public class AacScreens {

    public static final class AacScreenFlow extends SupportAppScreen {

        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, AacActivity.class);
        }
    }

    public static final class AacBlankSampleScreen extends AacScreen {

        @Override
        public int getNavigationResId() {
            return R.id.next_action_aac_blank;
        }
    }

    public static final class AacFirstSampleScreen extends AacScreen {

        @Override
        public int getNavigationResId() {
            return R.id.next_action_aac_first;
        }
    }

    public static final class AacSecondSampleScreen extends AacScreen {

        @Override
        public int getNavigationResId() {
            return R.id.next_action_aac_second;
        }
    }
}
