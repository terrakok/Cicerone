package ru.terrakok.cicerone.sample.mvp.bottom;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

/**
 * Created by terrakok 25.11.16
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface BottomNavigationView extends MvpView {
}
