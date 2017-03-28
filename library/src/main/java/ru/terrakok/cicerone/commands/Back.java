package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Rolls back the last transition from the screens chain.
 */
public class Back extends Command {

    /**
     * Creates a {@link Back} navigation command.
     */
    public Back() {
        super(CommandType.BACK);
    }
}
