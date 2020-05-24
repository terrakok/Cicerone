package com.github.terrakok.cicerone

/**
 * Navigation command describes screens transition.
 *
 * That can be processed by [com.github.terrakok.cicerone.Navigator]
 */
interface Command

/**
 * Opens new screen.
 */
data class Forward(
    val screen: Screen,
    val clearContainer: Boolean
) : Command

/**
 * Replaces the current screen.
 */
data class Replace(val screen: Screen) : Command

/**
 * Rolls fragmentBack the last transition from the screens chain.
 */
class Back : Command

/**
 * Rolls fragmentBack to the needed screen from the screens chain.
 *
 * Behavior in the case when no needed screens found depends on an implementation of the [com.github.terrakok.cicerone.Navigator]
 * But the recommended behavior is to return to the root.
 */
data class BackTo(val screen: Screen?) : Command