package com.github.terrakok.cicerone.androidx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen

sealed class AppScreen : Screen()

open class FragmentScreen(
    val createFragment: (FragmentFactory) -> Fragment
) : AppScreen()

open class ActivityScreen(
    val createIntent: (context: Context) -> Intent
) : AppScreen() {
    open val startActivityOptions: Bundle? = null
}