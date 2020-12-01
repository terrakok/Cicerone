package com.github.terrakok.cicerone.sample.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.cicerone.sample.databinding.FragmentForwardBinding
import com.github.terrakok.fondazione.AppFragment
import com.github.terrakok.fondazione.router

/**
 * Created by terrakok 26.11.16
 */
class ForwardFragment : AppFragment() {
    private lateinit var binding: FragmentForwardBinding
    private val containerName by lazy { arguments?.getString(EXTRA_NAME)!! }
    private val number by lazy { arguments?.getInt(EXTRA_NUMBER)!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentForwardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = containerName
        binding.toolbar.setNavigationOnClickListener { router.exit() }
        binding.forwardButton.setOnClickListener { router.navigateTo(Screens.Forward(containerName, number + 1)) }
        binding.githubButton.setOnClickListener { router.navigateTo(Screens.Github()) }

        var chain = "[0]"
        for (i in 0 until number) {
            chain += "➔" + (i + 1)
        }
        binding.chainText.text = chain
    }

    companion object {
        private const val EXTRA_NAME = "extra_name"
        private const val EXTRA_NUMBER = "extra_number"

        fun getNewInstance(name: String, number: Int) = ForwardFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_NAME, name)
                putInt(EXTRA_NUMBER, number)
            }
        }
    }
}