package com.github.terrakok.cicerone.sample.ui.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.terrakok.cicerone.ResultListenerHandler
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.cicerone.sample.ui.animations.AnimationFragment
import com.github.terrakok.fondazione.AppFragment
import com.github.terrakok.fondazione.router

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
class ProfileFragment : AppFragment() {
    private lateinit var avatar: ImageView
    val avatarViewForAnimation: View? get() = avatar

    private val RESULT_KEY = "photo_result"
    private var resultListenerHandler: ResultListenerHandler? = null
    private var selectedImage = R.drawable.ava_1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatar = view.findViewById(R.id.avatar_imageView)
        updatePhoto()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        avatar.transitionName = AnimationFragment.PHOTO_TRANSITION
        avatar.setOnClickListener {
            resultListenerHandler = router.setResultListener(RESULT_KEY) { data ->
                selectedImage = data as Int
                updatePhoto()
            }
            router.navigateTo(Screens.SelectPhoto(RESULT_KEY))
        }
    }

    private fun updatePhoto() {
        avatar.setImageResource(selectedImage)

        //for shared element animation
        avatar.tag = selectedImage
    }

    override fun onDestroy() {
        super.onDestroy()
        resultListenerHandler?.dispose()
    }
}