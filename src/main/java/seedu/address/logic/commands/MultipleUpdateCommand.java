package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.SingleUpdateCommand.UpdatePersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Updates the details of multiple existing persons in the address book.
 */
public class MultipleUpdateCommand extends Command {

    public static final String COMMAND_WORD = "update"; // Matches SingleUpdateCommand

    public static final String MESSAGE_UPDATE_MULTIPLE_SUCCESS = "Successfully updated: %1$s";

    private final List<Index> targetIndices;
    private final UpdatePersonDescriptor updatePersonDescriptor;

    /**
     * @param targetIndices of the persons in the filtered person list to edit
     * @param updatePersonDescriptor details to edit the persons with
     */
    public MultipleUpdateCommand(List<Index> targetIndices, UpdatePersonDescriptor updatePersonDescriptor) {
        requireNonNull(targetIndices);
        requireNonNull(updatePersonDescriptor);

        this.targetIndices = targetIndices;
        this.updatePersonDescriptor = new UpdatePersonDescriptor(updatePersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        StringBuilder updatedNames = new StringBuilder();

        // 1. Validate ALL indices first (Atomic Execution)
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        // 2. Perform the updates
        for (Index index : targetIndices) {
            Person personToUpdate = lastShownList.get(index.getZeroBased());
            Person updatedPerson = SingleUpdateCommand.createUpdatedPerson(personToUpdate, updatePersonDescriptor);

            if (!personToUpdate.isSamePerson(updatedPerson) && model.hasPerson(updatedPerson)) {
                throw new CommandException(SingleUpdateCommand.MESSAGE_DUPLICATE_PERSON
                        + " (Conflict at " + personToUpdate.getName() + ")");
            }

            model.setPerson(personToUpdate, updatedPerson);
            updatedNames.append(updatedPerson.getName()).append(", ");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // Remove the trailing comma and space
        String finalNames = updatedNames.substring(0, updatedNames.length() - 2);
        return new CommandResult(String.format(MESSAGE_UPDATE_MULTIPLE_SUCCESS, finalNames));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MultipleUpdateCommand otherCommand)) {
            return false;
        }
        return targetIndices.equals(otherCommand.targetIndices)
                && updatePersonDescriptor.equals(otherCommand.updatePersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .add("updatePersonDescriptor", updatePersonDescriptor)
                .toString();
    }
}
