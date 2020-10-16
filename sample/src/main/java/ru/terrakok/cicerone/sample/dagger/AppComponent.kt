package ru.terrakok.cicerone.sample.dagger

import dagger.Component
import ru.terrakok.cicerone.sample.dagger.module.LocalNavigationModule
import ru.terrakok.cicerone.sample.dagger.module.NavigationModule
import ru.terrakok.cicerone.sample.dagger.module.PhotoSelectionModule
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity
import ru.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment
import ru.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment
import ru.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity
import ru.terrakok.cicerone.sample.ui.bottom.TabContainerFragment
import ru.terrakok.cicerone.sample.ui.main.MainActivity
import ru.terrakok.cicerone.sample.ui.main.SampleFragment
import ru.terrakok.cicerone.sample.ui.start.StartActivity
import javax.inject.Singleton

/**
 * Created by terrakok 24.11.16
 */
@Singleton
@Component(modules = [
    NavigationModule::class,
    LocalNavigationModule::class,
    PhotoSelectionModule::class]
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
}