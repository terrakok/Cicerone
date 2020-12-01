package com.github.terrakok.fondazione

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlin.random.Random

open class NavigationFragment : AppFragment() {
    private lateinit var ciceroneKey: String
    private val cicerone: Cicerone<Router> by lazy {
        ciceroneMap.getOrPut(ciceroneKey) { Cicerone.create() }
    }

    val router: Router
        get() = cicerone.router
    val currentFragment: AppFragment?
        get() = childFragmentManager.findFragmentById(navigationContainerId) as? AppFragment

    private val navigator by lazy { createNavigator() }
    protected open fun createNavigator(): Navigator =
        object : AppNavigator(requireActivity(), navigationContainerId, childFragmentManager) {
            override fun activityBack() {
                findParentRouter()?.exit() ?: super.activityBack()
            }
        }

    protected open val navigationContainerId: Int = Random.nextInt()
    protected open val rootScreen: Screen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ciceroneKey = savedInstanceState?.getString(STATE_CICERONE_KEY) ?: "NavigationFragment_${hashCode()}"

        if (childFragmentManager.fragments.isEmpty()) {
            rootScreen?.let { router.newRootScreen(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FrameLayout(requireContext()).apply {
        id = navigationContainerId
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_CICERONE_KEY, ciceroneKey)
    }

    override fun onFinalDestroy() {
        super.onFinalDestroy()
        ciceroneMap.remove(ciceroneKey)
    }

    override fun onBackPressed(): Boolean {
        if (currentFragment?.onBackPressed() != true) router.exit()
        return true
    }

    companion object {
        private const val STATE_CICERONE_KEY = "state_cicerone_key"
        private val ciceroneMap = mutableMapOf<String, Cicerone<Router>>()
    }
}