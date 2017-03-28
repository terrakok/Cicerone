package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import javax.inject.Inject;

import ru.terrakok.cicerone.DefaultNavigator;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
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
    private TabContainerFragment androidTabFragment;
    private TabContainerFragment bugTabFragment;
    private TabContainerFragment dogTabFragment;

    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

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
        initContainers();

        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(ANDROID_TAB_POSITION, true);
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
                    case ANDROID_TAB_POSITION:
                        presenter.onTabAndroidClick();
                        break;
                    case BUG_TAB_POSITION:
                        presenter.onTabBugClick();
                        break;
                    case DOG_TAB_POSITION:
                        presenter.onTabDogClick();
                        break;
                }
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

    private void initContainers() {
        FragmentManager fm = getSupportFragmentManager();
        androidTabFragment = (TabContainerFragment) fm.findFragmentByTag("ANDROID");
        if (androidTabFragment == null) {
            androidTabFragment = TabContainerFragment.getNewInstance("ANDROID");
            fm.beginTransaction()
                    .add(R.id.ab_container, androidTabFragment, "ANDROID")
                    .detach(androidTabFragment).commitNow();
        }

        bugTabFragment = (TabContainerFragment) fm.findFragmentByTag("BUG");
        if (bugTabFragment == null) {
            bugTabFragment = TabContainerFragment.getNewInstance("BUG");
            fm.beginTransaction()
                    .add(R.id.ab_container, bugTabFragment, "BUG")
                    .detach(bugTabFragment).commitNow();
        }

        dogTabFragment = (TabContainerFragment) fm.findFragmentByTag("DOG");
        if (dogTabFragment == null) {
            dogTabFragment = TabContainerFragment.getNewInstance("DOG");
            fm.beginTransaction()
                    .add(R.id.ab_container, dogTabFragment, "DOG")
                    .detach(dogTabFragment).commitNow();
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.ab_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            presenter.onBackPressed();
        }
    }

    private Navigator navigator = new DefaultNavigator() {
        @Override
        public void applyReplace(String screenKey, Object transitionData) {
            FragmentManager fm = getSupportFragmentManager();

            switch (screenKey) {
                case Screens.ANDROID_SCREEN:
                    fm.beginTransaction()
                            .detach(bugTabFragment)
                            .detach(dogTabFragment)
                            .attach(androidTabFragment)
                            .commitNow();
                    break;
                case Screens.BUG_SCREEN:
                    fm.beginTransaction()
                            .detach(androidTabFragment)
                            .detach(dogTabFragment)
                            .attach(bugTabFragment)
                            .commitNow();
                    break;
                case Screens.DOG_SCREEN:
                    fm.beginTransaction()
                            .detach(androidTabFragment)
                            .detach(bugTabFragment)
                            .attach(dogTabFragment)
                            .commitNow();
                    break;
            }
        }

        @Override
        public void applyBack() {
            finish();
        }

        @Override
        public void applySystemMessage(String message) {
            Toast.makeText(BottomNavigationActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void highlightTab(int position) {
        bottomNavigationBar.selectTab(position, false);
    }

    @Override
    public Router getRouter() {
        return router;
    }
}
