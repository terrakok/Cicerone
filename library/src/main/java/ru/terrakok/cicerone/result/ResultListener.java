/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.result;

public interface ResultListener {

    /**
     * Received result from screen.
     *
     * @param resultData
     */
    void onResult(Object resultData);
}
