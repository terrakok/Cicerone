package com.github.terrakok.cicerone.sample.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.fondazione.AppFragment
import com.github.terrakok.fondazione.NavigationFragment
import com.github.terrakok.fondazione.router

class BottomNavigationFragment : AppFragment() {
    private val currentFragment: TabContainerFragment?
        get() = childFragmentManager.fragments.firstOrNull { it.isVisible } as? TabContainerFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_bottom, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNavigationBar = view.findViewById<BottomNavigationBar>(R.id.ab_bottom_navigation_bar)

        bottomNavigationBar
            .addItem(BottomNavigationItem(R.drawable.ic_android_white_24dp, R.string.tab_android))
            .addItem(BottomNavigationItem(R.drawable.ic_bug_report_white_24dp, R.string.tab_bug))
            .addItem(BottomNavigationItem(R.drawable.ic_pets_white_24dp, R.string.tab_dog))
            .initialise()
        bottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                when (position) {
                    0 -> selectTab("ANDROID")
                    1 -> selectTab("BUG")
                    2 -> selectTab("DOG")
                }
                bottomNavigationBar.selectTab(position, false)
            }

            override fun onTabUnselected(position: Int) {}

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }
        })
        if (currentFragment == null) bottomNavigationBar.selectTab(0)
    }

    private fun selectTab(tab: String) {
        val fm = childFragmentManager
        val current = currentFragment
        val newFragment = fm.findFragmentByTag(tab)
        if (current != null && newFragment != null && current === newFragment) return
        fm.beginTransaction().apply {
            if (newFragment == null) add(
                R.id.ab_container,
                TabContainerFragment.getNewInstance(tab),
                tab
            )
            if (current != null) hide(current)
            if (newFragment != null) show(newFragment)
        }.commit()
    }

    override fun onBackPressed(): Boolean {
        if (currentFragment?.onBackPressed() != true) router.exit()
        return true
    }
}

class TabContainerFragment : NavigationFragment() {
    override val rootScreen by lazy { Screens.Forward(arguments?.getString(EXTRA_NAME)!!, 0) }

    companion object {
        private const val EXTRA_NAME = "tcf_extra_name"

        fun getNewInstance(name: String) = TabContainerFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_NAME, name)
            }
        }
    }
}