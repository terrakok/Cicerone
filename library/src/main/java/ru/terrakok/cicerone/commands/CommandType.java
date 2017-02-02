package ru.terrakok.cicerone.commands;

/**
 * Created by Alexey on 30.12.2016.
 */

public interface CommandType {
    int TYPE_BACK = 1;
    int TYPE_BACK_TO = 2;
    int TYPE_FORWARD = 4;
    int TYPE_REPLACE = 8;
    int TYPE_SYSTEM_MESSAGE = 16;
}
