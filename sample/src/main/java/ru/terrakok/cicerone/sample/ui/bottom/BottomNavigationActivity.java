package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.mvp.bottom.BottomNavigationPresenter;
import ru.terrakok.cicerone.sample.mvp.bottom.BottomNavigationView;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;
import ru.terrakok.cicerone.sample.ui.common.RouterProvider;

/**
 * Created by terrakok 25.11.16
 */
public class BottomNavigationActivity extends MvpAppCompatActivity implements BottomNavigationView, RouterProvider {
    private BottomNavigationBar bottomNavigationBar;

    @Inject
    Router router;

    @InjectPresenter
    BottomNavigationPresenter presenter;

    @ProvidePresenter
    public BottomNavigationPresenter createBottomNavigationPresenter() {
        return new BottomNavigationPresenter(router);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.ab_bottom_navigation_bar);

        initViews();

        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(0, true);
        }
    }

    private void initViews() {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_android_white_24dp, R.string.tab_android))
                .addItem(new BottomNavigationItem(R.drawable.ic_bug_report_white_24dp, R.string.tab_bug))
                .addItem(new BottomNavigationItem(R.drawable.ic_pets_white_24dp, R.string.tab_dog))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        selectTab("ANDROID");
                        break;
                    case 1:
                        selectTab("BUG");
                        break;
                    case 2:
                        selectTab("DOG");
                        break;
                }
                bottomNavigationBar.selectTab(position, false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                onTabSelected(position);
            }
        });

    }

    private void selectTab(String tab) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment currentFragment = null;
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f.isVisible()) {
                    currentFragment = f;
                    break;
                }
            }
        }
        Fragment newFragment = fm.findFragmentByTag(tab);

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return;

        FragmentTransaction transaction = fm.beginTransaction();
        if (newFragment == null) {
            transaction.add(R.id.ab_container, new Screens.TabScreen(tab).getFragment(), tab);
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
            transaction.show(newFragment);
        }
        transaction.commitNow();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f.isVisible()) {
                    fragment = f;
                    break;
                }
            }
        }
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            presenter.onBackPressed();
        }
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
