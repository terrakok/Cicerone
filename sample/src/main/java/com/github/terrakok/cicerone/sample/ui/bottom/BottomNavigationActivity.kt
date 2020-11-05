package com.github.terrakok.cicerone.sample.ui.bottom

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.Screens.Tab
import com.github.terrakok.cicerone.sample.mvp.bottom.BottomNavigationPresenter
import com.github.terrakok.cicerone.sample.mvp.bottom.BottomNavigationView
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import com.github.terrakok.cicerone.sample.ui.common.RouterProvider
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by terrakok 25.11.16
 */
class BottomNavigationActivity : MvpAppCompatActivity(), BottomNavigationView, RouterProvider {

    private lateinit var bottomNavigationBar: BottomNavigationBar

    @Inject
    override lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: BottomNavigationPresenter

    @ProvidePresenter
    fun createBottomNavigationPresenter() = BottomNavigationPresenter(router)

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bottom)
        bottomNavigationBar = findViewById<View>(R.id.ab_bottom_navigation_bar) as BottomNavigationBar

        initViews()
        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(0, true)
        }
    }

    private fun initViews() {
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
    }

    private fun selectTab(tab: String) {
        val fm = supportFragmentManager
        var currentFragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = fm.findFragmentByTag(tab)
        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return
        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(
                    R.id.ab_container,
                    Tab(tab).createFragment(fm.fragmentFactory), tab
            )
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        if (fragment != null && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            presenter.onBackPressed()
        }
    }
}