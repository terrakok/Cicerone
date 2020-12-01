package com.github.terrakok.cicerone.sample.ui.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.databinding.FragmentSelectPhotoBinding
import com.github.terrakok.fondazione.AppFragment
import com.github.terrakok.fondazione.router

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
class SelectPhotoFragment : AppFragment() {
    private lateinit var binding: FragmentSelectPhotoBinding
    private val resultKey by lazy { arguments?.getString(EXTRA_RESULT_KEY)!! }

    private val clickListener = View.OnClickListener { v ->
        setTransitionName(v.tag as Int)
        router.sendResult(resultKey, v.tag as Int)
        router.exit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSelectPhotoBinding.inflate(inflater, container, false)
        binding.selectImage1.apply {
            setImageResource(R.drawable.ava_1)
            tag = R.drawable.ava_1
            setOnClickListener(clickListener)
        }
        binding.selectImage2.apply {
            setImageResource(R.drawable.ava_2)
            tag = R.drawable.ava_2
            setOnClickListener(clickListener)
        }
        binding.selectImage3.apply {
            setImageResource(R.drawable.ava_3)
            tag = R.drawable.ava_3
            setOnClickListener(clickListener)
        }
        binding.selectImage4.apply {
            setImageResource(R.drawable.ava_4)
            tag = R.drawable.ava_4
            setOnClickListener(clickListener)
        }
        setTransitionName(arguments?.getInt(ARG_ANIM_DESTINATION)!!)
        return binding.root
    }

    private fun setTransitionName(viewTag: Int) {
        arrayListOf(
            binding.selectImage1,
            binding.selectImage2,
            binding.selectImage3,
            binding.selectImage4
        ).forEach {
            it.transitionName =
                if (it.tag == viewTag) AnimationFragment.PHOTO_TRANSITION
                else null
        }
    }

    fun setAnimationDestinationId(resId: Int) {
        var arguments = arguments
        if (arguments == null) arguments = Bundle()
        arguments.putInt(ARG_ANIM_DESTINATION, resId)
        setArguments(arguments)
    }

    companion object {
        private const val ARG_ANIM_DESTINATION = "arg_anim_dest"
        private const val EXTRA_RESULT_KEY = "extra_result_key"

        fun getNewInstance(resultKey: String) = SelectPhotoFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_RESULT_KEY, resultKey)
            }
        }
    }
}