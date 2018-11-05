package ru.terrakok.cicerone.sample.ui.main;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class MainActivity extends MvpAppCompatActivity implements ChainHolder {
    private TextView screensSchemeTV;
    private List<WeakReference<Fragment>> chain = new ArrayList<>();

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportAppNavigator(this, R.id.main_container) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
            printScreensScheme();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        screensSchemeTV = (TextView) findViewById(R.id.screens_scheme);

        if (savedInstanceState == null) {
            navigator.applyCommands(new Command[]{new Replace(new Screens.SampleScreen(1))});
        } else {
            printScreensScheme();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    private void printScreensScheme() {
        ArrayList<SampleFragment> fragments = new ArrayList<>();
        for (WeakReference<Fragment> fragmentReference : chain) {
          Fragment fragment = fragmentReference.get();
          if (fragment != null && fragment instanceof SampleFragment) {
            fragments.add((SampleFragment) fragment);
          }
        }
        Collections.sort(fragments, new Comparator<SampleFragment>() {
            @Override
            public int compare(SampleFragment f1, SampleFragment f2) {
                long t = f1.getCreationTime() - f2.getCreationTime();
                if (t > 0) return 1;
                else if (t < 0) return -1;
                else return 0;
            }
        });

        ArrayList<Integer> keys = new ArrayList<>();
        for (SampleFragment fragment : fragments) {
            keys.add(fragment.getNumber());
        }
        screensSchemeTV.setText("Chain: " + keys.toString() + "");
    }

    @Override
    public List<WeakReference<Fragment>> getChain() {
        return chain;
    }
}
