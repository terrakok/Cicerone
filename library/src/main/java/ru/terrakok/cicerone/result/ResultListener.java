package ru.terrakok.cicerone.result;

import android.support.annotation.NonNull;

/**
 * @author Konstantin Tskhovrebov (aka terrakok) on 04.07.17.
 */

public interface ResultListener {

    /**
     * Received result from screen.
     *
     * @param resultData
     */
    void onResult(@NonNull Object resultData);
}
