package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Navigation command describes screens transition.
 * that can be processed by {@link ru.terrakok.cicerone.Navigator}.
 */
public abstract class Command {

    public final CommandType type;

    protected Command(CommandType type) {
        this.type = type;
    }

}
