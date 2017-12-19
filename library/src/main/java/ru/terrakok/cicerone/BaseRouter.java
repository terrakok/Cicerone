package ru.terrakok.cicerone;

import ru.terrakok.cicerone.commands.Command;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 12.10.16
 */

/**
 * BaseRouter is an abstract class to implement high-level navigation.
 * Extend it to add needed transition methods.
 */
public abstract class BaseRouter {
    private CommandBuffer commandBuffer;

    public BaseRouter() {
        this.commandBuffer = new CommandBuffer();
    }

    CommandBuffer getCommandBuffer() {
        return commandBuffer;
    }

    /**
     * Sends navigation command array to {@link CommandBuffer}.
     *
     * @param commands navigation command array to execute
     */
    protected void executeCommands(Command... commands) {
        commandBuffer.executeCommands(commands);
    }
}
