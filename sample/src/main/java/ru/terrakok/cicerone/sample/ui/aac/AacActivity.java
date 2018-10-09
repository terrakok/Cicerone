package ru.terrakok.cicerone.sample.ui.aac;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.aac.AacNavigator;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;

public class AacActivity extends MvpAppCompatActivity {

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Router router;

    private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aac);
        getOrCreateNavigator();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(getOrCreateNavigator());
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }

    private Navigator getOrCreateNavigator() {
        if (navigator == null) {
            navigator = new AacNavigator(this, R.id.nav_host_fragment);
        }
        return navigator;
    }
}
