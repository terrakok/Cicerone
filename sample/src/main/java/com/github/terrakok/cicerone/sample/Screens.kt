package com.github.terrakok.cicerone.sample

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.github.terrakok.cicerone.sample.ui.animations.ProfileActivity
import com.github.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment
import com.github.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment
import com.github.terrakok.cicerone.sample.ui.bottom.BottomNavigationActivity
import com.github.terrakok.cicerone.sample.ui.bottom.ForwardFragment
import com.github.terrakok.cicerone.sample.ui.bottom.TabContainerFragment
import com.github.terrakok.cicerone.sample.ui.main.MainActivity
import com.github.terrakok.cicerone.sample.ui.main.SampleFragment
import com.github.terrakok.cicerone.sample.ui.start.StartActivity

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */
object Screens {

    fun sampleScreen(number: Int) = FragmentScreen("SampleScreen_$number") {
        SampleFragment.getNewInstance(number)
    }

    fun startScreen() = ActivityScreen("StartScreen") {
        Intent(it, StartActivity::class.java)
    }

    fun mainScreen() = ActivityScreen("MainScreen") {
        Intent(it, MainActivity::class.java)
    }

    fun bottomNavigationScreen() = ActivityScreen("BottomNavigationScreen") {
        Intent(it, BottomNavigationActivity::class.java)
    }

    fun tabScreen(tabName: String) = FragmentScreen("FragmentScreen") {
        TabContainerFragment.getNewInstance(tabName)
    }

    fun forwardScreen(containerName: String, number: Int) = FragmentScreen("ForwardScreen") {
        ForwardFragment.getNewInstance(containerName, number)
    }

    fun githubScreen() = ActivityScreen("GithubScreen") {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"))
    }

    fun profileScreen() = ActivityScreen("ProfileScreen") {
        Intent(it, ProfileActivity::class.java)
    }

    fun profileInfoScreen() = FragmentScreen("ProfileInfoScreen") {
        ProfileFragment()
    }

    fun selectPhotoScreen() = FragmentScreen("SelectPhotoScreen") {
        SelectPhotoFragment()
    }
}