package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;
import javax.inject.Named;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;
import ru.terrakok.cicerone.sample.ui.common.RouterProvider;
import ru.terrakok.cicerone.sample.ui.main.SampleFragment;

/**
 * Created by terrakok 25.11.16
 */
public class TabContainerFragment extends Fragment implements RouterProvider, BackButtonListener {
    private static final String EXTRA_NAME = "tcf_extra_name";

    private Navigator navigator;

    @Inject
    @Named("LOCAL")
    Cicerone<Router> cicerone;

    public static TabContainerFragment getNewInstance(String name) {
        TabContainerFragment fragment = new TabContainerFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_container, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.tab_name)).setText(getArguments().getString(EXTRA_NAME));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getChildFragmentManager().findFragmentById(R.id.ftc_container) == null) {
            cicerone.getRouter().replaceScreen(Screens.SAMPLE_SCREEN, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        cicerone.getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        cicerone.getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportFragmentNavigator(getChildFragmentManager(), R.id.ftc_container) {

                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    return SampleFragment.getNewInstance((Integer) data);
                }

                @Override
                protected void showSystemMessage(String message) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void exit() {
                    ((RouterProvider) getActivity()).getRouter().exit();
                }
            };
        }
        return navigator;
    }

    @Override
    public Router getRouter() {
        return cicerone.getRouter();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.ftc_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            return false;
        }
    }
}
