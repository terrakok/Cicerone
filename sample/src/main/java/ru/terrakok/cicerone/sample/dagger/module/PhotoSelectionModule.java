package ru.terrakok.cicerone.sample.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.sample.R;
import ru.terrakok.cicerone.sample.mvp.animation.PhotoSelection;

/**
 * Created by terrakok 24.11.16
 */

@Module
public class PhotoSelectionModule {
    private PhotoSelection photoSelection = new PhotoSelection(R.drawable.ava_1);

    @Provides
    @Singleton
    PhotoSelection providePhotoSelectionr() {
        return photoSelection;
    }
}
