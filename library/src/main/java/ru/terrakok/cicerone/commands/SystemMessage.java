package ru.terrakok.cicerone.commands;

/**
 * Created by terrakok on 29.09.16.
 */

public class SystemMessage implements Command {
    private String message;

    public SystemMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
