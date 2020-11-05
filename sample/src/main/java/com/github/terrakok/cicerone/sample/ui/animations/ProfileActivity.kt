package com.github.terrakok.cicerone.sample.ui.animations

import android.os.Bundle
import android.transition.ChangeBounds
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.sample.R
import com.github.terrakok.cicerone.sample.SampleApplication
import com.github.terrakok.cicerone.sample.Screens.ProfileInfo
import com.github.terrakok.cicerone.sample.ui.animations.photos.SelectPhotoFragment
import com.github.terrakok.cicerone.sample.ui.animations.profile.ProfileFragment
import com.github.terrakok.cicerone.sample.ui.common.BackButtonListener
import javax.inject.Inject

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 14.07.17.
 */
class ProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        SampleApplication.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_container)
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(ProfileInfo())))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private val navigator: Navigator = object : AppNavigator(this, R.id.container) {

        override fun setupFragmentTransaction(fragmentTransaction: FragmentTransaction, currentFragment: Fragment?, nextFragment: Fragment?) {
            if (currentFragment is ProfileFragment
                    && nextFragment is SelectPhotoFragment) {
                setupSharedElementForProfileToSelectPhoto(
                        currentFragment,
                        nextFragment,
                        fragmentTransaction
                )
            }
        }
    }

    private fun setupSharedElementForProfileToSelectPhoto(profileFragment: ProfileFragment,
                                                          selectPhotoFragment: SelectPhotoFragment,
                                                          fragmentTransaction: FragmentTransaction) {
        val changeBounds = ChangeBounds()
        selectPhotoFragment.sharedElementEnterTransition = changeBounds
        selectPhotoFragment.sharedElementReturnTransition = changeBounds
        profileFragment.sharedElementEnterTransition = changeBounds
        profileFragment.sharedElementReturnTransition = changeBounds
        val view = profileFragment.avatarViewForAnimation
        fragmentTransaction.addSharedElement(view!!, PHOTO_TRANSITION)
        selectPhotoFragment.setAnimationDestinationId((view.tag as Int))
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment != null && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val PHOTO_TRANSITION = "photo_trasition"
    }
}