package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.Nullable;

/**
 * Navigator holder interface.
 * Use it to connect a {@link Navigator} to the {@link Cicerone}.
 */
public interface NavigatorHolder {

    /**
     * Set an active Navigator for the Cicerone and start receive commands.
     *
     * @param navigator new active Navigator, can be null
     */
    void setNavigator(@Nullable Navigator navigator);

    /**
     * Remove the current Navigator and stop receive commands.
     */
    void removeNavigator();
}
