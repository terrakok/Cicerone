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
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.mvp.start.StartActivityPresenter;
import ru.terrakok.cicerone.sample.mvp.start.StartActivityView;
import ru.terrakok.cicerone.sample.ui.main.MainActivity;

/**
 * Created by terrakok 21.11.16
 */
public class StartActivity extends MvpAppCompatActivity implements StartActivityView {
    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    Router router;

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
        findViewById(R.id.lets_go_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onNextPressed();
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
        public void applyCommand(Command command) {
            if (command instanceof Forward) {
                forward((Forward) command);
            } else if (command instanceof Replace) {
                replace((Replace) command);
            } else if (command instanceof Back) {
                back();
            } else if (command instanceof SystemMessage) {
                Toast.makeText(StartActivity.this, ((SystemMessage) command).getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Cicerone", "Illegal command for this screen: " + command.getClass().getSimpleName());
            }
        }

        private void forward(Forward command) {
            switch (command.getScreenKey()) {
                case Screens.START_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, StartActivity.class));
                    break;
                case Screens.MAIN_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    break;
                default:
                    Log.e("Cicerone", "Unknown screen: " + command.getScreenKey());
                    break;
            }
        }

        private void replace(Replace command) {
            switch (command.getScreenKey()) {
                case Screens.START_ACTIVITY_SCREEN:
                case Screens.MAIN_ACTIVITY_SCREEN:
                    forward(new Forward(command.getScreenKey(), command.getTransitionData()));
                    finish();
                    break;
                default:
                    Log.e("Cicerone", "Unknown screen: " + command.getScreenKey());
                    break;
            }
        }

        private void back() {
            finish();
        }
    };
}
