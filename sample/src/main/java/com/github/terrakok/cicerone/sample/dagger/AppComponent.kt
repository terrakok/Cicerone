package com.github.terrakok.cicerone.sample.dagger

import com.github.terrakok.cicerone.sample.dagger.module.LocalNavigationModule
import com.github.terrakok.cicerone.sample.dagger.module.NavigationModule
import com.github.terrakok.cicerone.sample.ui.animations.ProfileActivity
import com.github.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment
import com.github.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment
import com.github.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity
import com.github.terrakok.cicerone.sample.ui.bottom.TabContainerFragment
import com.github.terrakok.cicerone.sample.ui.graph.ForkFragment
import com.github.terrakok.cicerone.sample.ui.graph.GraphActivity
import com.github.terrakok.cicerone.sample.ui.graph.RoadFragment
import com.github.terrakok.cicerone.sample.ui.main.MainActivity
import com.github.terrakok.cicerone.sample.ui.main.SampleFragment
import com.github.terrakok.cicerone.sample.ui.start.StartActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by terrakok 24.11.16
 */
@Singleton
@Component(modules = [
    NavigationModule::class,
    LocalNavigationModule::class]
)
interface AppComponent {
    fun inject(activity: StartActivity)

    fun inject(activity: MainActivity)

    fun inject(fragment: SampleFragment)

    fun inject(activity: BottomNavigationActivity)

    fun inject(fragment: TabContainerFragment)

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: SelectPhotoFragment)

    fun inject(activity: ProfileActivity)

    fun inject(activity: GraphActivity)

    fun inject(fragment: RoadFragment)

    fun inject(fragment: ForkFragment)
}