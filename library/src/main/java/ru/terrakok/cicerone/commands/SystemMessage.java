package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import android.support.annotation.NonNull;

/**
 * Shows system message.
 */
public class SystemMessage implements Command {
    @NonNull
    private String message;

    /**
     * Creates a {@link SystemMessage} command.
     *
     * @param message message text
     */
    public SystemMessage(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public String getMessage() {
        return message;
    }
}
