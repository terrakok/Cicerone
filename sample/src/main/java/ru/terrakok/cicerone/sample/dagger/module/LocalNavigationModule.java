package ru.terrakok.cicerone.sample.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder;

/**
 * Created by terrakok 24.11.16
 */

@Module
public class LocalNavigationModule {

    @Provides
    @Singleton
    LocalCiceroneHolder provideLocalNavigationHolder() {
        return new LocalCiceroneHolder();
    }
}
