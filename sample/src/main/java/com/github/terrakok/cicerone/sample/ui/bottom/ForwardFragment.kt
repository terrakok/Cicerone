package com.github.terrakok.cicerone.sample.ui.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.terrakok.cicerone.sample.databinding.FragmentForwardBinding
import com.github.terrakok.cicerone.sample.mvp.bottom.forward.ForwardPresenter
import com.github.terrakok.cicerone.sample.mvp.bottom.forward.ForwardView
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import com.github.terrakok.cicerone.sample.ui.common.RouterProvider

/**
 * Created by terrakok 26.11.16
 */
class ForwardFragment : MvpAppCompatFragment(), ForwardView, BackButtonListener {
    private lateinit var binding: FragmentForwardBinding

    @InjectPresenter
    lateinit var presenter: ForwardPresenter

    @ProvidePresenter
    fun provideForwardPresenter(): ForwardPresenter {
        return ForwardPresenter(
                arguments!!.getString(EXTRA_NAME),
                (parentFragment as RouterProvider).router,
                arguments!!.getInt(EXTRA_NUMBER)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentForwardBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar.title = arguments!!.getString(EXTRA_NAME)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.forwardButton.setOnClickListener { presenter.onForwardPressed() }
        binding.githubButton.setOnClickListener { presenter.onGithubPressed() }
    }

    override fun setChainText(chainText: String) {
        binding.chainText.text = chainText
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    companion object {
        private const val EXTRA_NAME = "extra_name"
        private const val EXTRA_NUMBER = "extra_number"

        fun getNewInstance(name: String?, number: Int): ForwardFragment {
            return ForwardFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_NAME, name)
                    putInt(EXTRA_NUMBER, number)
                }
            }
        }
    }
}