package ru.terrakok.cicerone.sample.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.CommandType;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

public class MainActivity extends MvpAppCompatActivity {
    private static final String STATE_SCREEN_NAMES = "state_screen_names";

    private List<String> screenNames = new ArrayList<>();
    private TextView screensSchemeTV;

    @Inject
    NavigatorHolder navigatorHolder;

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(), R.id.main_container) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            return SampleFragment.getNewInstance((int) data);
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }

        @Override
        public void applyCommand(Command command) {
            super.applyCommand(command);
            updateScreenNames(command);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        screensSchemeTV = (TextView) findViewById(R.id.screens_scheme);

        if (savedInstanceState == null) {
            navigator.applyCommand(new Command(Screens.SAMPLE_SCREEN, 1, CommandType.TYPE_REPLACE));
        } else {
            screenNames = (List<String>) savedInstanceState.getSerializable(STATE_SCREEN_NAMES);
            printScreensScheme();
        }
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_SCREEN_NAMES, (Serializable) screenNames);
    }

    private void updateScreenNames(Command command) {
        switch (command.getCommandType()){
            case CommandType.TYPE_BACK:
                if (screenNames.size() > 0) {
                    screenNames.remove(screenNames.size() - 1);
                }
                break;
            case CommandType.TYPE_FORWARD:
                int forwardData = (int) command.getTransitionData();
                screenNames.add(forwardData + "");
                break;
            case CommandType.TYPE_REPLACE:
                int replaceData = (int) command.getTransitionData();
                if (screenNames.size() > 0) {
                    screenNames.remove(screenNames.size() - 1);
                }
                screenNames.add(replaceData + "");
                break;
            case CommandType.TYPE_BACK_TO:
                screenNames = new ArrayList<>(screenNames.subList(0, getSupportFragmentManager().getBackStackEntryCount() + 1));
                break;
        }

        printScreensScheme();
    }

    private void printScreensScheme() {
        String str = "";
        for (String name : screenNames) {
            if (!str.isEmpty()) {
                str += "➔" + name;
            } else {
                str = "[" + name + "]";
            }
        }
        screensSchemeTV.setText("Chain: " + str + "");
    }
}
