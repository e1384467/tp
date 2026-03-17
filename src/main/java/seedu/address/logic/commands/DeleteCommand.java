package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE_MULTIPLE_INDICES = COMMAND_WORD
            + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX,INDEX[,INDEX,...] (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1,2,3";

    public static final String MESSAGE_USAGE_RANGE_INDICES = COMMAND_WORD
            + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
            + "Parameters: START_INDEX-END_INDEX (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1-3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s): %1$s";

    private final Index[] targetIndices;

    public DeleteCommand(Index targetIndex) {
        this.targetIndices = new Index[] { targetIndex };
    }

    public DeleteCommand(Index[] targetIndices) {
        this.targetIndices = targetIndices;
    }

    /**
     * Constructor for DeleteCommand that takes in a start index and end index,
     * and deletes all people in the range of the two indices (inclusive).
     * @param startIndex
     * @param endIndex
     */
    public DeleteCommand(Index startIndex, Index endIndex) {
        int start = startIndex.getZeroBased();
        int end = endIndex.getZeroBased();
        assert startIndex.getZeroBased() <= endIndex.getZeroBased()
                : "Start index should be less than or equal to end index";;

        this.targetIndices = new Index[end - start + 1];
        for (int i = 0; i < targetIndices.length; i++) {
            targetIndices[i] = Index.fromZeroBased(start + i);
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person[] personsToDelete = new Person[targetIndices.length];
        for (int i = 0; i < targetIndices.length; i++) {
            Index targetIndex = targetIndices[i];
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INDICES);
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            personsToDelete[i] = personToDelete;
        }

        StringBuilder deletedPersonsString = new StringBuilder();
        for (Person person : personsToDelete) {
            model.deletePerson(person);
            deletedPersonsString.append("\n" + Messages.format(person));
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersonsString));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return Arrays.equals(targetIndices, otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", Arrays.toString(targetIndices))
                .toString();
    }
}
