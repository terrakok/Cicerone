package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Navigator holder interface.
 * Use it to provide Navigator for Cicerone.
 */
public interface NavigatorHolder {

    /**
     * Set active Navigator for Cicerone and start receive commands.
     * @param navigator new active Navigator
     */
    void setNavigator(Navigator navigator);

    /**
     * Remove current navigator and stop receive commands.
     */
    void removeNavigator();
}
