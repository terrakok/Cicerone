package com.github.terrakok.cicerone.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.databinding.FragmentSampleBinding
import com.github.terrakok.cicerone.sample.databinding.FragmentSemitransparentBinding
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.MvpAppCompatFragment
import javax.inject.Inject

class SemiTransparentFragment : BaseFragment(), BackButtonListener {
    lateinit var binding: FragmentSemitransparentBinding

    @Inject
    lateinit var router: Router

    override val name: String = "D"
    override val creationTime: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSemitransparentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.background.setOnClickListener { onBackPressed() }
        binding.backButton.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed(): Boolean {
        router.exit()
        return true
    }
}