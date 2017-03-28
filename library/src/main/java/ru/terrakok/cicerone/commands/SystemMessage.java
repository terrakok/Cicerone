package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Shows system message.
 */
public class SystemMessage extends Command {
    private String message;

    /**
     * Creates a {@link SystemMessage} command.
     *
     * @param message message text
     */
    public SystemMessage(String message) {
        super(CommandType.SYSTEM_MESSAGE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
