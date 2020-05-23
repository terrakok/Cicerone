package com.github.terrakok.cicerone.androidx

import android.os.Bundle
import androidx.fragment.app.Fragment

data class FragmentParams(
    val fragmentClass: Class<out Fragment>,
    val arguments: Bundle? = null
)