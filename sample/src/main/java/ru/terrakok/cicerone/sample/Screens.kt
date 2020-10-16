package ru.terrakok.cicerone.sample

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity
import ru.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment
import ru.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment
import ru.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity
import ru.terrakok.cicerone.sample.ui.bottom.ForwardFragment
import ru.terrakok.cicerone.sample.ui.bottom.TabContainerFragment
import ru.terrakok.cicerone.sample.ui.main.MainActivity
import ru.terrakok.cicerone.sample.ui.main.SampleFragment
import ru.terrakok.cicerone.sample.ui.start.StartActivity

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */
object Screens {

    @JvmStatic
    fun sampleScreen(number: Int) = FragmentScreen("SampleScreen_$number") {
        SampleFragment.getNewInstance(number)
    }

    @JvmStatic
    fun startScreen() = ActivityScreen("StartScreen") {
        Intent(it, StartActivity::class.java)
    }

    @JvmStatic
    fun mainScreen() = ActivityScreen("MainScreen") {
        Intent(it, MainActivity::class.java)
    }

    @JvmStatic
    fun bottomNavigationScreen() = ActivityScreen("BottomNavigationScreen") {
        Intent(it, BottomNavigationActivity::class.java)
    }

    @JvmStatic
    fun tabScreen(tabName: String) = FragmentScreen("FragmentScreen") {
        TabContainerFragment.getNewInstance(tabName)
    }

    @JvmStatic
    fun forwardScreen(containerName: String, number: Int) = FragmentScreen("ForwardScreen") {
        ForwardFragment.getNewInstance(containerName, number)
    }

    @JvmStatic
    fun githubScreen() = ActivityScreen("GithubScreen") {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"))
    }

    @JvmStatic
    fun profileScreen() = ActivityScreen("ProfileScreen") {
        Intent(it, ProfileActivity::class.java)
    }

    @JvmStatic
    fun profileInfoScreen() = FragmentScreen("ProfileInfoScreen") {
        ProfileFragment()
    }

    @JvmStatic
    fun selectPhotoScreen() = FragmentScreen("SelectPhotoScreen") {
        SelectPhotoFragment()
    }
}