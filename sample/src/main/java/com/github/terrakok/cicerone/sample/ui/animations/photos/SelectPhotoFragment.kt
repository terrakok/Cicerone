package com.github.terrakok.cicerone.sample.ui.animations.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.databinding.FragmentSelectPhotoBinding
import com.github.terrakok.cicerone.sample.mvp.animation.PhotoSelection
import com.github.terrakok.cicerone.sample.mvp.animation.photos.SelectPhotoPresenter
import com.github.terrakok.cicerone.sample.mvp.animation.photos.SelectPhotoView
import com.github.terrakok.cicerone.sample.ui.animations.ProfileActivity
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
class SelectPhotoFragment : MvpAppCompatFragment(), SelectPhotoView, BackButtonListener {

    private lateinit var binding: FragmentSelectPhotoBinding
    private lateinit var allImages: Array<ImageView>

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var photoSelection: PhotoSelection

    @InjectPresenter
    lateinit var presenter: SelectPhotoPresenter

    fun setAnimationDestinationId(resId: Int) {
        var arguments = arguments
        if (arguments == null) arguments = Bundle()
        arguments.putInt(ARG_ANIM_DESTINATION, resId)
        setArguments(arguments)
    }

    private val animationDestionationId: Int
        get() = arguments!!.getInt(ARG_ANIM_DESTINATION)

    @ProvidePresenter
    fun providePresenter() = SelectPhotoPresenter(photoSelection, router)

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSelectPhotoBinding.inflate(inflater, container, false)
        allImages = arrayOf(
                binding.selectImage1,
                binding.selectImage2,
                binding.selectImage3,
                binding.selectImage4
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        allImages.forEach {
            it.setOnClickListener(clickListener)
        }
    }

    private val clickListener = View.OnClickListener { v ->
        allImages.forEach {
            it.transitionName = null
        }
        v.transitionName = ProfileActivity.PHOTO_TRANSITION
        presenter.onPhotoClick((v.tag as Int))
    }

    override fun showPhotos(resourceIds: IntArray) {
        if (resourceIds.size >= 4) {
            //for shared element animation
            val animRes = animationDestionationId

            allImages.forEachIndexed {
                index, imageView ->
                imageView.setImageResource(resourceIds[index])
                imageView.tag = resourceIds[index]
                imageView.transitionName = if (animRes == resourceIds[index]) ProfileActivity.PHOTO_TRANSITION else null
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackPressed()
        return true
    }

    companion object {
        private const val ARG_ANIM_DESTINATION = "arg_anim_dest"
    }
}