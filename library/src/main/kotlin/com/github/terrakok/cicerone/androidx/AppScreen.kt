package com.github.terrakok.cicerone.androidx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen

sealed class AppScreen : Screen

open class FragmentScreen(
    private val key: String? = null,
    val createFragment: (FragmentFactory) -> Fragment
) : AppScreen() {
    override val screenKey: String get() = key ?: super.screenKey
}

open class ActivityScreen(
    private val key: String? = null,
    val createIntent: (context: Context) -> Intent
) : AppScreen() {
    override val screenKey: String get() = key ?: super.screenKey
    open val startActivityOptions: Bundle? = null
}