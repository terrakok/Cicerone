package ru.terrakok.cicerone.sample.dagger.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.Router;

/**
 * Created by terrakok 24.11.16
 */

@Module
public class NavigationModule {

    @Provides
    @Singleton
    @Named("GLOBAL")
    Cicerone<Router> provideGlobalCicerone() {
        return Cicerone.create();
    }

    @Provides
    @Named("LOCAL")
    Cicerone<Router> provideLocalCicerone() {
        return Cicerone.create();
    }
}
