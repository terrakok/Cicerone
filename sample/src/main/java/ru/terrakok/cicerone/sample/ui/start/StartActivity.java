package ru.terrakok.cicerone.sample.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.mvp.start.StartActivityPresenter;
import ru.terrakok.cicerone.sample.mvp.start.StartActivityView;
import ru.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity;
import ru.terrakok.cicerone.sample.ui.main.MainActivity;

/**
 * Created by terrakok 21.11.16
 */
public class StartActivity extends MvpAppCompatActivity implements StartActivityView {
    @Inject
    Router router;

    @Inject
    NavigatorHolder navigatorHolder;

    @InjectPresenter
    StartActivityPresenter presenter;

    @ProvidePresenter
    public StartActivityPresenter createStartActivityPresenter() {
        return new StartActivityPresenter(router);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.ordinary_nav_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onOrdinaryPressed();
            }
        });
        findViewById(R.id.multi_nav_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onMultiPressed();
            }
        });
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

    private Navigator navigator = new Navigator() {
        @Override
        public void applyReplace(String screenKey, Object transitionData) {
            switch (screenKey) {
                case Screens.START_ACTIVITY_SCREEN:
                case Screens.MAIN_ACTIVITY_SCREEN:
                case Screens.BOTTOM_NAVIGATION_ACTIVITY_SCREEN:
                    applyReplace(screenKey, transitionData);
                    finish();
                    break;
                default:
                    Log.e("Cicerone", "Unknown screen: " + screenKey);
                    break;
            }
        }

        @Override
        public void applyForward(String screenKey, Object transitionData) {
            switch (screenKey) {
                case Screens.START_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, StartActivity.class));
                    break;
                case Screens.MAIN_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    break;
                case Screens.BOTTOM_NAVIGATION_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, BottomNavigationActivity.class));
                    break;
                default:
                    Log.e("Cicerone", "Unknown screen: " + screenKey);
                    break;
            }
        }

        @Override
        public void applyBack() {
            finish();
        }

        @Override
        public void applyBackTo(String screenKey) {
            //empty
        }

        @Override
        public void applySystemMessage(String message) {
            Toast.makeText(StartActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}
