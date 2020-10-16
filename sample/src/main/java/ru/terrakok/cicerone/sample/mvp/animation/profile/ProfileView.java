package ru.terrakok.cicerone.sample.mvp.animation.profile;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ProfileView extends MvpView {
    void showPhoto(int resId);
}
