package com.github.terrakok.cicerone

/**
 * Screen is class for description application screen.
 */
abstract class Screen {
    open val screenKey: String = this::class.qualifiedName!!
}