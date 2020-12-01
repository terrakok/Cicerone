package com.github.terrakok.cicerone.sample.ui.animations

import android.transition.ChangeBounds
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.sample.Screens
import com.github.terrakok.fondazione.NavigationFragment

class AnimationFragment : NavigationFragment() {
    override val rootScreen = Screens.Profile()

    override fun createNavigator(): Navigator =
        object : AppNavigator(requireActivity(), navigationContainerId, childFragmentManager) {
            override fun setupFragmentTransaction(
                fragmentTransaction: FragmentTransaction,
                currentFragment: Fragment?,
                nextFragment: Fragment?
            ) {
                if (currentFragment is ProfileFragment && nextFragment is SelectPhotoFragment) {
                    setupSharedElementForProfileToSelectPhoto(currentFragment, nextFragment, fragmentTransaction)
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

    companion object {
        const val PHOTO_TRANSITION = "photo_trasition"
    }
}