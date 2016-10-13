package ru.terrakok.cicerone;

import java.util.LinkedList;
import java.util.Queue;

import ru.terrakok.cicerone.commands.Command;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 12.10.16
 */

/**
 * Passes navigation command to an active {@link Navigator}
 * or stores it in the pending commands queue to pass it later.
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
     * Passes {@code command} to the {@link Navigator} if it available.
     * Else puts it to the pending commands queue to pass it later.
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
