package ru.terrakok.cicerone

abstract class Screen {

    open val screenKey: String = javaClass.canonicalName
}