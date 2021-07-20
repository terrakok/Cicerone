package com.github.terrakok.cicerone.sample.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.Screens.Sample
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.MvpAppCompatActivity
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
class MainActivity : MvpAppCompatActivity(), ChainHolder {

    private lateinit var screensSchemeTV: TextView

    override val chain = ArrayList<WeakReference<Fragment>>()

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator = object : AppNavigator(this, R.id.main_container) {

        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
            printScreensScheme()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        screensSchemeTV = findViewById<View>(R.id.screens_scheme) as TextView

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(Sample(1))))
        } else {
            printScreensScheme()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (fragment != null && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

    private fun printScreensScheme() {
        val fragments = ArrayList<SampleFragment>()
        for (fragmentReference in chain) {
            val fragment = fragmentReference.get()
            if (fragment != null && fragment is SampleFragment) {
                fragments.add(fragment)
            }
        }
        fragments.sortWith { f1, f2 ->
            val t = f1.creationTime - f2.creationTime
            if (t > 0) 1 else if (t < 0) -1 else 0
        }
        val keys = ArrayList<Int>()
        for (fragment in fragments) {
            keys.add(fragment.number)
        }
        screensSchemeTV.text = "Chain: $keys"
    }
}