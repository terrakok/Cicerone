package ru.terrakok.cicerone.android.pure

import android.app.Fragment
import android.content.Context
import android.content.Intent
import ru.terrakok.cicerone.Screen

abstract class AppScreen : Screen() {

    open fun getFragment(): Fragment? = null

    open fun getActivityIntent(context: Context?): Intent? = null
}