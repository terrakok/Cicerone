package com.github.terrakok.cicerone.androidx

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen

sealed class AppScreen : Screen

fun interface Creator<A, R> {
    fun create(argument: A): R
}

open class FragmentScreen @JvmOverloads constructor(
    private val key: String? = null,
    private val fragmentCreator: Creator<FragmentFactory, Fragment>
) : AppScreen() {
    override val screenKey: String get() = key ?: super.screenKey
    fun createFragment(factory: FragmentFactory) = fragmentCreator.create(factory)
}

open class ActivityScreen @JvmOverloads constructor(
    private val key: String? = null,
    private val intentCreator: Creator<Context, Intent>
) : AppScreen() {
    override val screenKey: String get() = key ?: super.screenKey
    open val startActivityOptions: Bundle? = null
    fun createIntent(context: Context) = intentCreator.create(context)
}