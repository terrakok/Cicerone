package ru.terrakok.cicerone.android.support

import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentParams(
    private var fragmentClass: Class<out Fragment>,
    private var arguments: Bundle? = null
) {

    fun getFragmentClass() = fragmentClass

    fun getArguments() = arguments
}