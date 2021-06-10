package com.github.terrakok.cicerone.sample.ui.main

import android.os.Bundle
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class SemiTransparentFragment : MvpAppCompatFragment(R.layout.fragment_semitransparent),
    BackButtonListener {

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}