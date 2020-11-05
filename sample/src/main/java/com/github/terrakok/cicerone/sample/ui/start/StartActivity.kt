package com.github.terrakok.cicerone.sample.ui.start

import android.os.Bundle
import android.view.View
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.mvp.start.StartActivityPresenter
import com.github.terrakok.cicerone.sample.mvp.start.StartActivityView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by terrakok 21.11.16
 */
class StartActivity : MvpAppCompatActivity(), StartActivityView {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: StartActivityPresenter

    private val navigator: Navigator = AppNavigator(this, -1)

    @ProvidePresenter
    fun createStartActivityPresenter() = StartActivityPresenter(router)

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)
        initViews()
    }

    private fun initViews() {
        findViewById<View>(R.id.ordinary_nav_button).setOnClickListener {
            presenter.onOrdinaryPressed()
        }
        findViewById<View>(R.id.multi_nav_button).setOnClickListener {
            presenter.onMultiPressed()
        }
        findViewById<View>(R.id.result_and_anim_button).setOnClickListener {
            presenter.onResultWithAnimationPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}