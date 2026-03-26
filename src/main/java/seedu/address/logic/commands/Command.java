package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Checks if this command is undoable.
     * @return true if the command modifies data and can be undone, false otherwise
     */
    public boolean isUndoable() {
        return false;
    }

    /**
     * Undoes the command by reversing its effects on the model.
     * Default implementation does nothing (for commands that don't support undo).
     *
     * @param model {@code Model} which the command should operate on.
     * @throws CommandException If an error occurs during undo execution.
     */
    public void undo(Model model) throws CommandException {
        // Default: no-op for commands that don't support undo (e.g., help, exit, list, find, clear)
    }

}
