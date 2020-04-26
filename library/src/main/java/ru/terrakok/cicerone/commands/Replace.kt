package ru.terrakok.cicerone.commands

import ru.terrakok.cicerone.Screen

class Replace(private var screen: Screen) : Command {

    fun getScreen() = screen
}