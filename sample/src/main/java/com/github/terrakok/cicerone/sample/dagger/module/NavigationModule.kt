package com.github.terrakok.cicerone.sample.dagger.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.graph.GraphRouter
import com.github.terrakok.cicerone.sample.Graph
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by terrakok 24.11.16
 */
@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = create()
    private val graphCicerone: Cicerone<GraphRouter> = create(GraphRouter(Graph()))

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }

    @Provides
    @Singleton
    fun provideGraphRouter(): GraphRouter {
        return graphCicerone.router
    }

    @Provides
    @Singleton
    @Named("graph")
    fun provideGraphNavigatorHolder(): NavigatorHolder {
        return graphCicerone.getNavigatorHolder()
    }
}