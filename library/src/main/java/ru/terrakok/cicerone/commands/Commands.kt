package ru.terrakok.cicerone.commands


/**
 * Rolls back the last transition from the screens chain.
 *
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
class Back : Command


/**
 * Rolls back to the needed screen from the screens chain.
 *
 * Behavior in the case when no needed screens found depends on an implementation of the [ru.terrakok.cicerone.Navigator].
 * But the recommended behavior is to return to the root.
 *
 * @param screenKey screen key or null if you need back to root screen
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
class BackTo(val screenKey: String?) : Command


/**
 * Opens new screen.
 *
 * @param screenKey      screen key
 * @param transitionData initial data, can be null
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
class Forward(val screenKey: String, val transitionData: Any?) : Command


/**
 * Replaces the current screen.
 *
 * @param screenKey      screen key
 * @param transitionData initial data, can be null
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
class Replace(val screenKey: String, val transitionData: Any?) : Command


/**
 * Shows system message.
 *
 * @param message message text
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
class SystemMessage(val message: String) : Command
