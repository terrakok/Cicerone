package com.github.terrakok.cicerone.sample

import android.content.Intent
import android.net.Uri
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.github.terrakok.cicerone.sample.ui.StartFragment
import com.github.terrakok.cicerone.sample.ui.animations.AnimationFragment
import com.github.terrakok.cicerone.sample.ui.animations.SelectPhotoFragment
import com.github.terrakok.cicerone.sample.ui.animations.ProfileFragment
import com.github.terrakok.cicerone.sample.ui.bottom.BottomNavigationFragment
import com.github.terrakok.cicerone.sample.ui.bottom.ForwardFragment
import com.github.terrakok.cicerone.sample.ui.main.MainFragment
import com.github.terrakok.cicerone.sample.ui.main.SampleFragment

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
object Screens {
    fun Start() = FragmentScreen { StartFragment() }

    fun Main() = FragmentScreen { MainFragment() }
    fun Sample(number: Int) = FragmentScreen("Sample($number)") {
        SampleFragment.getNewInstance(number)
    }

    fun BottomNavigation() = FragmentScreen { BottomNavigationFragment() }
    fun Forward(containerName: String, number: Int) = FragmentScreen {
        ForwardFragment.getNewInstance(containerName, number)
    }
    fun Github() = ActivityScreen {
        Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"))
    }

    fun Animation() = FragmentScreen { AnimationFragment() }
    fun Profile() = FragmentScreen { ProfileFragment() }
    fun SelectPhoto(resultKey: String) = FragmentScreen {
        SelectPhotoFragment.getNewInstance(resultKey)
    }
}