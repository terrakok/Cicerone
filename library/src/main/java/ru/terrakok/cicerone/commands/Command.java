package ru.terrakok.cicerone.commands;

/**
 * Created by Konstantin Tckhovrebov (aka @terrakok)
 * on 11.10.16
 */

/**
 * Navigation command describes screens transition.
 * that can be processed by {@link ru.terrakok.cicerone.Navigator}.
 */
public class Command {
    private String screenKey;
    private Object transitionData;
    private int commandType;

    public Command(){}

    public Command(int commandType){
        this.commandType = commandType;
    }

    public Command(String screenKey, Object transitionData, int commandType){
        this.screenKey = screenKey;
        this.transitionData = transitionData;
        this.commandType = commandType;
    }


    public String getScreenKey() {
        return screenKey;
    }

    public void setScreenKey(String screenKey) {
        this.screenKey = screenKey;
    }

    public Object getTransitionData() {
        return transitionData;
    }

    public void setTransitionData(Object transitionData) {
        this.transitionData = transitionData;
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }
}
