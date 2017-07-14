package ru.terrakok.cicerone.sample.mvp.animation.photos;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SelectPhotoView extends MvpView {
    void showPhotos(int[] resurceIds);
}
