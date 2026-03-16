package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT =
            "I have saved your data! Hope to see you again!";

    public static final String MESSAGE_USAGE =
            "Usage: exit";

    public static final String MESSAGE_EXTRA_PARAMETERS = "There should not be extra parameters.\nUsage: exit";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }
}
