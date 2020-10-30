package com.github.terrakok.cicerone.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.databinding.FragmentSampleBinding
import com.github.terrakok.cicerone.sample.mvp.main.SamplePresenter
import com.github.terrakok.cicerone.sample.mvp.main.SampleView
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
class SampleFragment : BaseFragment(), SampleView, BackButtonListener {

    lateinit var binding: FragmentSampleBinding

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: SamplePresenter

    @ProvidePresenter
    fun createSamplePresenter() = SamplePresenter(router, arguments!!.getInt(EXTRA_NUMBER))

    val number: Int
        get() = arguments!!.getInt(EXTRA_NUMBER)
    val creationTime: Long
        get() = arguments!!.getLong(EXTRA_TIME, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSampleBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbar.setNavigationOnClickListener { presenter.onBackCommandClick() }
        binding.backCommand.setOnClickListener { presenter.onBackCommandClick() }
        binding.forwardCommand.setOnClickListener { presenter.onForwardCommandClick() }
        binding.replaceCommand.setOnClickListener { presenter.onReplaceCommandClick() }
        binding.newChainCommand.setOnClickListener { presenter.onNewChainCommandClick() }
        binding.newRootChainCommand.setOnClickListener { presenter.onNewRootChainCommandClick() }
        binding.newRootCommand.setOnClickListener { presenter.onNewRootCommandClick() }
        binding.forwardDelayCommand.setOnClickListener { presenter.onForwardWithDelayCommandClick() }
        binding.backToCommand.setOnClickListener { presenter.onBackToCommandClick() }
        binding.finishChainCommand.setOnClickListener { presenter.onFinishChainCommandClick() }
    }

    override fun setTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackCommandClick()
        return true
    }

    companion object {

        private const val EXTRA_NUMBER = "extra_number"
        private const val EXTRA_TIME = "extra_time"

        fun getNewInstance(number: Int): SampleFragment {
            return SampleFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_NUMBER, number)
                    putLong(EXTRA_TIME, System.currentTimeMillis())
                }
            }
        }
    }

}