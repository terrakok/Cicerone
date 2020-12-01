package com.github.terrakok.cicerone.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.cicerone.sample.databinding.FragmentSampleBinding
import com.github.terrakok.fondazione.AppFragment
import com.github.terrakok.fondazione.router

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 * on 11.10.16
 */
class SampleFragment : AppFragment() {
    lateinit var binding: FragmentSampleBinding

    val number by lazy { arguments!!.getInt(EXTRA_NUMBER) }
    val creationTime by lazy { arguments!!.getLong(EXTRA_TIME, 0L) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment as? MainFragment)?.fragmentChain?.add(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSampleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "Screen $number"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { router.exit() }
        binding.backCommand.setOnClickListener { router.exit() }
        binding.forwardCommand.setOnClickListener { router.navigateTo(Screens.Sample(number + 1)) }
        binding.replaceCommand.setOnClickListener { router.replaceScreen(Screens.Sample(number + 1)) }
        binding.newChainCommand.setOnClickListener {
            router.newChain(
                Screens.Sample(number + 1),
                Screens.Sample(number + 2),
                Screens.Sample(number + 3)
            )
        }
        binding.newRootChainCommand.setOnClickListener {
            router.newRootChain(
                Screens.Sample(number + 1),
                Screens.Sample(number + 2),
                Screens.Sample(number + 3)
            )
        }
        binding.newRootCommand.setOnClickListener { router.newRootScreen(Screens.Sample(number + 1)) }
        binding.forwardDelayCommand.setOnClickListener {
            view?.postDelayed({
                router.navigateTo(Screens.Sample(number + 1))
            }, 5000)
        }
        binding.backToCommand.setOnClickListener { router.backTo(Screens.Sample(3)) }
        binding.finishChainCommand.setOnClickListener { router.finishChain() }
    }

    override fun onFinalDestroy() {
        super.onFinalDestroy()
        (parentFragment as? MainFragment)?.fragmentChain?.remove(this)
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