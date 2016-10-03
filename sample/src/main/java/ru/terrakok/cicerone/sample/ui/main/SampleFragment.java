package ru.terrakok.cicerone.sample.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.mvp.main.SamplePresenter;
import ru.terrakok.cicerone.sample.mvp.main.SampleView;
import ru.terrakok.cicerone.sample.ui.global.MvpFragment;

/**
 * Created by terrakok 01.10.16
 */
public class SampleFragment extends MvpFragment implements SampleView {
    private static final String EXTRA_NUMBER = "extra_number";

    private Toolbar toolbar;
    private View backCommandBt;
    private View forwardCommandBt;
    private View replaceCommandBt;
    private View newChainCommandBt;
    private View newRootCommandBt;
    private View backWithMessageCommandBt;
    private View forwardWithDelayCommandBt;
    private View backToCommandBt;

    @InjectPresenter
    SamplePresenter presenter;

    public static SampleFragment getNewInstance(int number) {
        SampleFragment fragment = new SampleFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_NUMBER, number);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.init(getArguments().getInt(EXTRA_NUMBER));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        backCommandBt = view.findViewById(R.id.back_command);
        forwardCommandBt = view.findViewById(R.id.forward_command);
        replaceCommandBt = view.findViewById(R.id.replace_command);
        newChainCommandBt = view.findViewById(R.id.new_chain_command);
        newRootCommandBt = view.findViewById(R.id.new_root_command);
        backWithMessageCommandBt = view.findViewById(R.id.back_with_message_command);
        forwardWithDelayCommandBt = view.findViewById(R.id.forward_delay_command);
        backToCommandBt = view.findViewById(R.id.back_to_command);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackCommandClick();
            }
        });
        backCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackCommandClick();
            }
        });
        forwardCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onForwardCommandClick();
            }
        });
        replaceCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onReplaceCommandClick();
            }
        });
        newChainCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onNewChainCommandClick();
            }
        });
        newRootCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onNewRootCommandClick();
            }
        });
        backWithMessageCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackWithMessageCommandClick();
            }
        });
        forwardWithDelayCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onForwardWithDelayCommandClick();
            }
        });
        backToCommandBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onBackToCommandClick();
            }
        });
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    public void onBackPressed() {
        presenter.onBackCommandClick();
    }
}
