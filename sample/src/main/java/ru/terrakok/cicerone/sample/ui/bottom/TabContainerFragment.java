package ru.terrakok.cicerone.sample.ui.bottom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.Screens;
import ru.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;
import ru.terrakok.cicerone.sample.ui.common.RouterProvider;

/**
 * Created by terrakok 25.11.16
 */
public class TabContainerFragment extends Fragment implements RouterProvider, BackButtonListener {
    private static final String EXTRA_NAME = "tcf_extra_name";

    private Navigator navigator;

    @Inject
    LocalCiceroneHolder ciceroneHolder;

    public static TabContainerFragment getNewInstance(String name) {
        TabContainerFragment fragment = new TabContainerFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    private String getContainerName() {
        return getArguments().getString(EXTRA_NAME);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    private Cicerone<Router> getCicerone() {
        return ciceroneHolder.getCicerone(getContainerName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getChildFragmentManager().findFragmentById(R.id.ftc_container) == null) {
            getCicerone().getRouter().replaceScreen(Screens.FORWARD_SCREEN, 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCicerone().getNavigatorHolder().setNavigator(getNavigator());
    }

    @Override
    public void onPause() {
        getCicerone().getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    private Navigator getNavigator() {
        if (navigator == null) {
            navigator = new SupportAppNavigator(getActivity(), getChildFragmentManager(), R.id.ftc_container) {

                @Override
                protected Intent createActivityIntent(Context context, String screenKey, Object data) {
                    if (screenKey.equals(Screens.GITHUB_SCREEN)) {
                        return new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"));
                    }
                    return null;
                }

                @Override
                protected Fragment createFragment(String screenKey, Object data) {
                    if (screenKey.equals(Screens.FORWARD_SCREEN)) {
                        return ForwardFragment.getNewInstance(getContainerName(), (int) data);
                    }
                    return null;
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
        return getCicerone().getRouter();
    }

    @Override
    public boolean onBackPressed() {
        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.ftc_container);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return true;
        } else {
            ((RouterProvider) getActivity()).getRouter().exit();
            return true;
        }
    }
}
