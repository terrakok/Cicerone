package com.github.terrakok.cicerone.sample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.fondazione.NavigationFragment
import com.github.terrakok.fondazione.findParentRouter

class MainFragment : NavigationFragment() {
    private lateinit var textView: TextView

    override val navigationContainerId = R.id.main_container
    override val rootScreen = Screens.Sample(1)

    val fragmentChain = mutableSetOf<SampleFragment>()

    override fun createNavigator() =
        object : AppNavigator(requireActivity(), navigationContainerId, childFragmentManager) {
            override fun applyCommands(commands: Array<out Command>) {
                super.applyCommands(commands)
                printScreenChain()
            }
            override fun activityBack() {
                findParentRouter()?.exit() ?: super.activityBack()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.screens_scheme)
    }

    private fun printScreenChain() {
        childFragmentManager.executePendingTransactions()
        textView.text = fragmentChain
            .sortedBy { it.creationTime }
            .joinToString(prefix = "Chain:", separator = "➔") { it.number.toString() }
    }
}