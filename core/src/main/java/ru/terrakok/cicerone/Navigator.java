package ru.terrakok.cicerone;

/*
 * Created by Konstantin Tckhovrebov (aka terrakok) on 29.09.16 17:00.
 */

import ru.terrakok.cicerone.commands.Command;

public interface Navigator {
    void applyCommand(Command command);
}
