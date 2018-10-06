package ru.terrakok.cicerone.sample.ui.aac;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.SampleApplication;
import ru.terrakok.cicerone.sample.mvp.aac.AacSecondSamplePresenter;
import ru.terrakok.cicerone.sample.mvp.aac.AacSecondSampleView;

public class AacSecondSampleFragment extends MvpAppCompatFragment implements AacSecondSampleView {

    @Inject
    Router router;

    @InjectPresenter
    AacSecondSamplePresenter presenter;

    @ProvidePresenter
    AacSecondSamplePresenter providePresenter() {
        return new AacSecondSamplePresenter(router);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SampleApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_aac_second_sample, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Aac second fragment");
        Button btn = rootView.findViewById(R.id.btn_aac_blank);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAacBlankPressed();
            }
        });
        return rootView;
    }
}
