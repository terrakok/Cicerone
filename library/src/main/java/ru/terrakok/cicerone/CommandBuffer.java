/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone;

import java.util.LinkedList;
import java.util.Queue;

import ru.terrakok.cicerone.commands.Command;

/**
 * Passes navigation command to an active {@link Navigator}
 * or stores it in the pending commands queue to pass it later.
 */
class CommandBuffer implements NavigatorHolder {
    private Navigator navigator;
    private Queue<Command[]> pendingCommands = new LinkedList<>();

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
        while (!pendingCommands.isEmpty()) {
            if (navigator != null) {
                executeCommands(pendingCommands.poll());
            } else break;
        }
    }

    @Override
    public void removeNavigator() {
        this.navigator = null;
    }

    /**
     * Passes {@code commands} to the {@link Navigator} if it available.
     * Else puts it to the pending commands queue to pass it later.
     * @param commands navigation command array
     */
    void executeCommands(Command[] commands) {
        if (navigator != null) {
            navigator.applyCommands(commands);
        } else {
            pendingCommands.add(commands);
        }
    }
}
