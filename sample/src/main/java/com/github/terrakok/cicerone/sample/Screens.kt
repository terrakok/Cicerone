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
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
object Screens {

    fun Sample(number: Int) = FragmentScreen("Sample($number)") {
        SampleFragment.getNewInstance(number)
    }

    fun Start() = ActivityScreen {
        Intent(it, StartActivity::class.java)
    }

    fun Main() = ActivityScreen {
        Intent(it, MainActivity::class.java)
    }

    fun BottomNavigation() = ActivityScreen {
        Intent(it, BottomNavigationActivity::class.java)
    }

    fun Tab(tabName: String) = FragmentScreen {
        TabContainerFragment.getNewInstance(tabName)
    }

    fun Forward(containerName: String, number: Int) = FragmentScreen {
        ForwardFragment.getNewInstance(containerName, number)
    }

    fun Github() = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"))
    }

    fun Profile() = ActivityScreen {
        Intent(it, ProfileActivity::class.java)
    }

    fun ProfileInfo() = FragmentScreen {
        ProfileFragment()
    }

    fun SelectPhoto(resultKey: String) = FragmentScreen {
        SelectPhotoFragment.getNewInstance(resultKey)
    }
}