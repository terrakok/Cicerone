package com.github.terrakok.cicerone

/**
 * Screen is interface for description application screen.
 */
interface Screen {
    val screenKey: String get() = this::class.java.name
}