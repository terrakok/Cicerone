package com.github.terrakok.cicerone.sample.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.graph.GraphRouter
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.databinding.FragmentForkBinding
import com.github.terrakok.cicerone.sample.databinding.FragmentRoadBinding
import com.github.terrakok.cicerone.sample.mvp.graph.ForkPresenter
import com.github.terrakok.cicerone.sample.mvp.graph.RoadPresenter
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class ForkFragment : MvpAppCompatFragment(), MvpView, BackButtonListener {
    lateinit var binding: FragmentForkBinding

    @Inject
    lateinit var graphRouter: GraphRouter

    @InjectPresenter
    lateinit var presenter: ForkPresenter

    @ProvidePresenter
    fun provideRoadPresenter(): ForkPresenter {
        return ForkPresenter(graphRouter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentForkBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.textView.text = arguments?.getString(EXTRA_NUMBER)
        binding.topButton.setOnClickListener { presenter.onTopButtonClick() }
        binding.bottomButton.setOnClickListener { presenter.onBottomButtonClick()}
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    companion object {
        private const val EXTRA_NUMBER = "extra_number"

        fun getNewInstance(number: String) = ForkFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_NUMBER, number)
            }
        }
    }
}