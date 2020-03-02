package ru.terrakok.cicerone.sample.mvp.main;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SampleView extends MvpView {
    void setTitle(String title);
}
