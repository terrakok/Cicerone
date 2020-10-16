package ru.terrakok.cicerone.sample.ui.animations.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.terrakok.cicerone.Router
import ru.terrakok.cicerone.sample.R
import ru.terrakok.cicerone.sample.SampleApplication
import ru.terrakok.cicerone.sample.mvp.animation.PhotoSelection
import ru.terrakok.cicerone.sample.mvp.animation.profile.ProfilePresenter
import ru.terrakok.cicerone.sample.mvp.animation.profile.ProfileView
import ru.terrakok.cicerone.sample.ui.animations.ProfileActivity
import ru.terrakok.cicerone.sample.ui.common.BackButtonListener
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