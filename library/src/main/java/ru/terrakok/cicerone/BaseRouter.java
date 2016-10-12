package ru.terrakok.cicerone;

import ru.terrakok.cicerone.commands.Command;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 12.10.16
 */

public abstract class BaseRouter {
    private CommandBuffer commandBuffer;

    public BaseRouter() {
        this.commandBuffer = new CommandBuffer();
    }

    public CommandBuffer getCommandBuffer() {
        return commandBuffer;
    }

    /**
     * Send navigation command to CommandBuffer.
     * @param command navigation command
     */
    protected void executeCommand(Command command) {
        commandBuffer.executeCommand(command);
    }
}
