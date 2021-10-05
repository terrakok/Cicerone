package com.github.terrakok.fondazione

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator

open class AppActivity : AppCompatActivity() {
    private lateinit var ciceroneKey: String
    private val cicerone: Cicerone<Router> by lazy {
        ciceroneMap.getOrPut(ciceroneKey) { Cicerone.create() }
    }

    val router: Router
        get() = cicerone.router
    val currentFragment: AppFragment?
        get() = supportFragmentManager.findFragmentById(navigationContainerId) as? AppFragment

    private val navigator by lazy { createNavigator() }
    protected open fun createNavigator(): Navigator = AppNavigator(this, navigationContainerId)

    protected open val navigationContainerId: Int = ViewCompat.generateViewId()
    protected open val rootScreen: Screen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            FrameLayout(this).apply {
                id = navigationContainerId
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            }
        )

        ciceroneKey = savedInstanceState?.getString(STATE_CICERONE_KEY) ?: "AppActivity_${hashCode()}"

        if (supportFragmentManager.fragments.isEmpty()) {
            rootScreen?.let { router.newRootScreen(it) }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            ciceroneMap.remove(ciceroneKey)
        }
    }

    override fun onBackPressed() {
        if (currentFragment?.onBackPressed() != true) {
            router.exit()
        }
    }

    companion object {
        private const val STATE_CICERONE_KEY = "state_cicerone_key"
        private val ciceroneMap = mutableMapOf<String, Cicerone<Router>>()
    }
}