package ru.terrakok.cicerone.android;


import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.commands.Back;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;
import ru.terrakok.cicerone.commands.SystemMessage;

/**
 * {@link Navigator} basic implementation with applyCommand dispatching.
 * <p>
 * (@Link #applyCommand(command)) method dispatches passed command to corresponding methods.
 * </p>
 */
public abstract class NavigatorBase implements Navigator {

    /**
     * Performs transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    @Override
    public void applyCommand(Command command) {
        if (command instanceof Forward) {
            applyForward((Forward) command);
        } else if (command instanceof Back) {
            applyBack((Back) command);
        } else if (command instanceof Replace) {
            applyReplace((Replace) command);
        } else if (command instanceof BackTo) {
            applyBackTo((BackTo) command);
        } else if (command instanceof SystemMessage) {
            showSystemMessage(((SystemMessage) command).getMessage());
        }
    }

    /**
     * Opens new screen based on the passed command.
     *
     * @param forward forward command to apply
     */
    protected abstract void applyForward(Forward forward);

    /**
     * Rolls back the last transition from the screens chain based on the passed command.
     *
     * @param back back command to apply
     */
    protected abstract void applyBack(Back back);

    /**
     * Replaces the current screen based on the passed command.
     *
     * @param replace replace command to apply
     */
    protected abstract void applyReplace(Replace replace);

    /**
     * Rolls back to the needed screen from the screens chain based on the passed command.
     *
     * @param backTo backTo command to apply
     */
    protected abstract void applyBackTo(BackTo backTo);

    /**
     * Shows system message.
     *
     * @param message message to show
     */
    protected abstract void showSystemMessage(String message);

    /**
     * Called when we try to back from the root.
     */
    protected abstract void exit();

    /**
     * Called when we tried to back to some specific screen, but didn't found it.
     */
    protected abstract void backToUnexisting();

    /**
     * Called if we can't create a screen.
     */
    protected void unknownScreen(Command command) {
        throw new RuntimeException("Can't create a screen for passed screenKey.");
    }
}
