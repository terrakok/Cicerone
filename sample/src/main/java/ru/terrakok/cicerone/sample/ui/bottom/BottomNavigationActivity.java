package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.mvp.bottom.BottomNavigationPresenter;
import ru.terrakok.cicerone.sample.mvp.bottom.BottomNavigationView;

/**
 * Created by terrakok 25.11.16
 */
public class BottomNavigationActivity extends MvpAppCompatActivity implements BottomNavigationView {
    private BottomNavigationBar bottomNavigationBar;
    private TabContainerFragment androidTabFragment;
    private TabContainerFragment bugTabFragment;
    private TabContainerFragment dogTabFragment;

    @Inject
    NavigatorHolder navigatorHolder;

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

        androidTabFragment = TabContainerFragment.getNewInstance("ANDROID");
        bugTabFragment = TabContainerFragment.getNewInstance("BUG");
        dogTabFragment = TabContainerFragment.getNewInstance("DOG");
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(), R.id.ab_container) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey) {
                case Screens.ANDROID_SCREEN:
                    return androidTabFragment;
                case Screens.BUG_SCREEN:
                    return bugTabFragment;
                case Screens.DOG_SCREEN:
                    return dogTabFragment;
            }
            return null;
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(BottomNavigationActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };

    @Override
    public void highlightTab(int position) {
        bottomNavigationBar.selectTab(position, false);
    }
}
