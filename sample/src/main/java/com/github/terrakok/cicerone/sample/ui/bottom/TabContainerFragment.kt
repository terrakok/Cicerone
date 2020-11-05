package com.github.terrakok.cicerone.sample.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.Screens.Forward
import com.github.terrakok.cicerone.sample.subnavigation.LocalCiceroneHolder
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import com.github.terrakok.cicerone.sample.ui.common.RouterProvider
import javax.inject.Inject

/**
 * Created by terrakok 25.11.16
 */
class TabContainerFragment : Fragment(), RouterProvider, BackButtonListener {

    private val navigator: Navigator by lazy {
        AppNavigator(activity!!, R.id.ftc_container, childFragmentManager)
    }

    @Inject
    lateinit var ciceroneHolder: LocalCiceroneHolder

    private val containerName: String
        get() = arguments!!.getString(EXTRA_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    private val cicerone: Cicerone<Router>
        get() = ciceroneHolder.getCicerone(containerName)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.ftc_container) == null) {
            cicerone.router.replaceScreen(Forward(containerName, 0))
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    override val router: Router
        get() = cicerone.router

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.ftc_container)
        return if (fragment != null && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            true
        } else {
            (activity as RouterProvider?)!!.router.exit()
            true
        }
    }

    companion object {
        private const val EXTRA_NAME = "tcf_extra_name"

        fun getNewInstance(name: String?) =
                TabContainerFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_NAME, name)
                    }
                }
    }
}