package ru.terrakok.cicerone

import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class Router() : BaseRouter() {

    fun navigateTo(screen: Screen) {
        executeCommands(Forward(screen))
    }

    fun newRootScreen(screen: Screen) {
        executeCommands(
            BackTo(null),
            Replace(screen)
        )
    }

    fun replaceScreen(screen: Screen) {
        executeCommands(Replace(screen))
    }

    fun backTo(screen: Screen?) {
        executeCommands(BackTo(screen))
    }

    fun newChain(vararg screens: Screen) {
        val commands = arrayOfNulls<Command>(screens.size)

        screens.forEachIndexed { index, screen ->
            commands[index] = Forward(screen)
        }

        executeCommands(*commands.filterNotNull().toTypedArray())
    }

    fun newRootChain(vararg screens: Screen) {
        val commands = arrayOfNulls<Command>(screens.size + 1)

        commands[0] = BackTo(null)
        if (screens.isNotEmpty()) {
            commands[1] = Replace(screens[0])
            for (i in 1 until screens.size) {
                commands[i + 1] = Forward(screens[i])
            }
        }

        executeCommands(*commands.filterNotNull().toTypedArray())
    }

    fun finishChain() {
        executeCommands(
            BackTo(null),
            Back()
        )
    }

    fun exit() {
        executeCommands(Back())
    }
}