package ru.terrakok.cicerone.sample.subnavigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.Router
import java.util.*

/**
 * Created by terrakok 27.11.16
 */
class LocalCiceroneHolder {
    private val containers = HashMap<String, Cicerone<Router>>()

    fun getCicerone(containerTag: String): Cicerone<Router> =
            containers.getOrPut(containerTag) {
                create()
            }
}