package ru.terrakok.cicerone.sample.mvp.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by terrakok 01.10.16
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SampleView extends MvpView {
    void setTitle(String title);
}
