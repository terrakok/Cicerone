package ru.terrakok.cicerone.sample.mvp.bottom;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by terrakok 25.11.16
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface BottomNavigationView extends MvpView {
    int ANDROID_TAB_POSITION = 0;
    int BUG_TAB_POSITION = 1;
    int DOG_TAB_POSITION = 2;

    void highlightTab(int position);
}
