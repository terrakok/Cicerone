package com.github.terrakok.cicerone.sample.ui.main

import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

interface ChainHolder {
    val chain: MutableList<WeakReference<Fragment>>
}

interface ChainScreen {
    val name: String
    val creationTime: Long
}