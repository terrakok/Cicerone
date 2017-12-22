package ru.terrakok.cicerone

/**
 * Navigator holder interface.
 * Use it to connect a [Navigator] to the [Cicerone].
 *
 * @author Konstantin Tskhovrebov (aka terrakok) on 11.10.16.
 */
interface NavigatorHolder {

    /**
     * Set an active Navigator for the Cicerone and start receive commands.
     *
     * @param navigator new active Navigator, can be null
     */
    fun setNavigator(navigator: Navigator)

    /**
     * Remove the current Navigator and stop receive commands.
     */
    fun removeNavigator()
}
