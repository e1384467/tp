package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose details match any of "
            + "the specified search parameters (case-insensitive) and displays them as a list.\n"
            + "Parameters:\n"
            + "  pn/NAME...\n"
            + "  ic/IC_NUMBER\n"
            + "  p/PHONE_NUMBER\n"
            + "You can specify one or more of the above. At least one must be provided.\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " pn/Alice Bob\n"
            + "  " + COMMAND_WORD + " ic/S1234567A\n"
            + "  " + COMMAND_WORD + " p/91234567";

    private final Predicate<Person> predicate;
    private final String criteriaDescription;

    /**
     * Creates a {@code FindCommand} with the given {@code predicate} and a
     * description of the search criteria.
     *
     * @param predicate predicate used to filter patients
     * @param criteriaDescription description of the criteria shown in the result message
     */
    public FindCommand(Predicate<Person> predicate, String criteriaDescription) {
        this.predicate = predicate;
        this.criteriaDescription = criteriaDescription;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int count = model.getFilteredPersonList().size();
        String header = String.format("Found %d patient(s) whose identifiers include the following criteria: ", count);
        String details = criteriaDescription == null ? "" : criteriaDescription;
        return new CommandResult(header + details);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("criteriaDescription", criteriaDescription)
                .toString();
    }
}
