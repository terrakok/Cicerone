package ru.terrakok.cicerone.sample.ui.bottom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.mvp.bottom.forward.ForwardPresenter;
import ru.terrakok.cicerone.sample.mvp.bottom.forward.ForwardView;
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener;
import ru.terrakok.cicerone.sample.ui.common.RouterProvider;

/**
 * Created by terrakok 26.11.16
 */
public class ForwardFragment extends MvpAppCompatFragment implements ForwardView, BackButtonListener {
    private static final String EXTRA_NAME = "extra_name";
    private static final String EXTRA_NUMBER = "extra_number";

    private Toolbar toolbar;
    private TextView chainTV;
    private View forwardBt;
    private View githubBt;

    @InjectPresenter
    ForwardPresenter presenter;

    @ProvidePresenter
    ForwardPresenter provideForwardPresenter() {
        return new ForwardPresenter(
                getArguments().getString(EXTRA_NAME),
                ((RouterProvider) getParentFragment()).getRouter(),
                getArguments().getInt(EXTRA_NUMBER)
        );
    }

    public static ForwardFragment getNewInstance(String name, int number) {
        ForwardFragment fragment = new ForwardFragment();

        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_NAME, name);
        arguments.putInt(EXTRA_NUMBER, number);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forward, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        chainTV = (TextView) view.findViewById(R.id.chain_text);
        forwardBt = view.findViewById(R.id.forward_button);
        githubBt = view.findViewById(R.id.github_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setTitle(getArguments().getString(EXTRA_NAME));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackPressed();
            }
        });
        forwardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onForwardPressed();
            }
        });
        githubBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onGithubPressed();
            }
        });
    }

    @Override
    public void setChainText(String chainText) {
        chainTV.setText(chainText);
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }
}
