package ru.terrakok.cicerone.commands

import ru.terrakok.cicerone.Screen

class Forward(private var screen: Screen) : Command {

    fun getScreen() = screen
}