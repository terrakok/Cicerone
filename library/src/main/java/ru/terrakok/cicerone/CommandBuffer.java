package ru.terrakok.cicerone;

import java.util.LinkedList;
import java.util.Queue;

import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

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
            switch (command.type) {
                case REPLACE:
                    Replace replace = (Replace) command;
                    navigator.applyReplace(replace.getScreenKey(), replace.getTransitionData());
                    break;
                case FORWARD:
                    Forward forward = (Forward) command;
                    navigator.applyForward(forward.getScreenKey(), forward.getTransitionData());
                    break;
                case BACK:
                    Back back = (Back) command;
                    navigator.applyBack();
                    break;
                case BACK_TO:
                    BackTo backTo = (BackTo) command;
                    navigator.applyBackTo(backTo.getScreenKey());
                    break;
                case SYSTEM_MESSAGE:
                    SystemMessage systemMessage = (SystemMessage) command;
                    navigator.applySystemMessage(systemMessage.getMessage());
                    break;
            }
        } else {
            pendingCommands.add(command);
        }
    }
}
