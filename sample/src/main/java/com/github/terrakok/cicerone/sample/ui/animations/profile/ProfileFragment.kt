package com.github.terrakok.cicerone.sample.ui.animations.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.mvp.animation.PhotoSelection
import com.github.terrakok.cicerone.sample.mvp.animation.profile.ProfilePresenter
import com.github.terrakok.cicerone.sample.mvp.animation.profile.ProfileView
import com.github.terrakok.cicerone.sample.ui.animations.ProfileActivity
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
class ProfileFragment : MvpAppCompatFragment(), ProfileView, BackButtonListener {

    private lateinit var avatar: ImageView

    @Inject
    lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @Inject
    lateinit var photoSelection: PhotoSelection

    @ProvidePresenter
    fun providePresenter() = ProfilePresenter(photoSelection, router)

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        avatar = view.findViewById<View>(R.id.avatar_imageView) as ImageView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        avatar.transitionName = ProfileActivity.PHOTO_TRANSITION
        avatar.setOnClickListener { presenter.onPhotoClicked() }
    }

    override fun showPhoto(resId: Int) {
        avatar.setImageResource(resId)

        //for shared element animation
        avatar.tag = resId
    }

    val avatarViewForAnimation: View?
        get() = avatar

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }
}