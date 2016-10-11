package ru.terrakok.cicerone;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

import ru.terrakok.cicerone.commands.Command;

public interface Navigator {
    void applyCommand(Command command);
}
