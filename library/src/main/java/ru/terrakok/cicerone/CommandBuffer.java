package ru.terrakok.cicerone;

import java.util.LinkedList;
import java.util.Queue;

import ru.terrakok.cicerone.commands.Command;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 12.10.16
 */

class CommandBuffer implements NavigatorHolder {
    private Navigator navigator;
    private Queue<Command> pendingCommands = new LinkedList<>();

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
        while (!pendingCommands.isEmpty()) {
            if (navigator != null) {
                executeCommand(pendingCommands.poll());
            } else break;
        }
    }

    @Override
    public void removeNavigator() {
        this.navigator = null;
    }

    /**
     * Send navigation command to active navigator
     * or add it to pending commands queue.
     * @param command navigation command
     */
    public void executeCommand(Command command) {
        if (navigator != null) {
            navigator.applyCommand(command);
        } else {
            pendingCommands.add(command);
        }
    }
}
