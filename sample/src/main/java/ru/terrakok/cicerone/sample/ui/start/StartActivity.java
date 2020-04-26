package ru.terrakok.cicerone.sample.ui.start;

import android.os.Bundle;
import android.view.View;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.mvp.start.StartActivityPresenter;
import ru.terrakok.cicerone.sample.mvp.start.StartActivityView;

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

    private Navigator navigator = new SupportAppNavigator(this, -1);

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
        findViewById(R.id.result_and_anim_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onResultWithAnimationPressed();
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
}
