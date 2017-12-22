package ru.terrakok.cicerone.result

/**
 * @author Konstantin Tskhovrebov (aka terrakok) on 04.07.17.
 */
interface ResultListener {

    /**
     * Received result from screen.
     *
     * @param resultData
     */
    fun onResult(resultData: Any)
}
