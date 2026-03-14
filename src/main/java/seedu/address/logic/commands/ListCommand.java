package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "There are %d patient record(s).";

    public static final String MESSAGE_EMPTY_LIST = "Your patient record list is currently empty.";

    public static final String MESSAGE_EXTRA_PARAMETERS = "The 'list' command does not take any parameters.\n"
            public static final String MESSAGE_USAGE = COMMAND_WORD 
            + ": Lists all patients\n"
            + "Parameters: NIL\n"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        int numberOfPatients = model.getFilteredPersonList().size();

        if (numberOfPatients == 0) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numberOfPatients));
    }
}
