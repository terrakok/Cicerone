package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Command

interface Navigator {

    fun applyCommands(commands: Array<out Command>)
}