package ru.terrakok.cicerone.android.support

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen

abstract class SupportAppScreen : Screen() {

    open fun getFragment(): Fragment? = null

    open fun getActivityIntent(context: Context): Intent? = null

    open fun getFragmentParams(): FragmentParams? = null
}