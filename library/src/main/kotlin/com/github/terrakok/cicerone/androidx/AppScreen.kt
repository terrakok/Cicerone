package com.github.terrakok.cicerone.androidx

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Screen

abstract class AppScreen : Screen() {
    open fun getFragment(): Fragment? = null
    open fun getActivityIntent(context: Context): Intent? = null
    open fun getFragmentParams(): FragmentParams? = null
}