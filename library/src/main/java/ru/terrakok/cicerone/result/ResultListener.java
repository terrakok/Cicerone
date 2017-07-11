package ru.terrakok.cicerone.result;

/**
 * @author Konstantin Tskhovrebov (aka terrakok) on 04.07.17.
 */

public interface ResultListener {

    /**
     * Received result from screen.
     *
     * @param resultData
     */
    void onResult(Object resultData);
}
