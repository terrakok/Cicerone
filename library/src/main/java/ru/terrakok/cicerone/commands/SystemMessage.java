/*
 * Created by Konstantin Tskhovrebov (aka @terrakok)
 */

package ru.terrakok.cicerone.commands;

/**
 * Shows system message.
 */
public class SystemMessage implements Command {
    private String message;

    /**
     * Creates a {@link SystemMessage} command.
     *
     * @param message message text
     */
    public SystemMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
