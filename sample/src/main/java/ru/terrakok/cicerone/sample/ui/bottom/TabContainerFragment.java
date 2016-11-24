package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.terrakok.cicerone.sample.R;

/**
 * Created by terrakok 25.11.16
 */
public class TabContainerFragment extends Fragment {
    private static final String EXTRA_NAME = "tcf_extra_name";

    public static TabContainerFragment getNewInstance(String name) {
        TabContainerFragment fragment = new TabContainerFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView textView = new TextView(getContext());
        textView.setText(getArguments().getString(EXTRA_NAME));
        ((FrameLayout) getView().findViewById(R.id.ftc_container)).addView(textView);
    }
}
