package com.github.terrakok.cicerone.sample.ui

import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.fondazione.AppFragment
import com.github.terrakok.fondazione.router

class StartFragment : AppFragment(R.layout.fragment_start) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.apply {
            findViewById<View>(R.id.ordinary_nav_button).setOnClickListener {
                router.navigateTo(Screens.Main())
            }
            findViewById<View>(R.id.multi_nav_button).setOnClickListener {
                router.navigateTo(Screens.BottomNavigation())
            }
            findViewById<View>(R.id.result_and_anim_button).setOnClickListener {
                router.navigateTo(Screens.Animation())
            }
        }
    }
}