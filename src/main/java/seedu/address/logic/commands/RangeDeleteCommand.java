package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a range of people identified using their displayed index from the address book.
 */
public class RangeDeleteCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
            + "Parameters: START_INDEX-END_INDEX (must be positive integers and START_INDEX <= END_INDEX)\n"
            + "Example: " + COMMAND_WORD + " 1-3";

    private final Index startIndex;
    private final Index endIndex;

    /**
     * Creates a RangeDeleteCommand to delete the specified range of people.
     *
     * @param startIndex The starting index of the range (inclusive).
     * @param endIndex The ending index of the range (inclusive).
     */
    public RangeDeleteCommand(Index startIndex, Index endIndex, Set<Prefix> prefixes) {
        super(prefixes);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.getErrorMessageForNoPersons(COMMAND_WORD));
        }

        if (startIndex.getZeroBased() >= lastShownList.size() || endIndex.getZeroBased() >= lastShownList.size()) {
            Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
            throw new CommandException(Messages.getErrorMessageForInvalidIndices(lastIndex));
        }

        if (startIndex.getZeroBased() > endIndex.getZeroBased()) {
            throw new CommandException(Messages.getErrorMessageForInvalidRangeIndices());
        }

        int numberOfPersonsToDelete = endIndex.getZeroBased() - startIndex.getZeroBased() + 1;
        Person[] personsToDelete = new Person[numberOfPersonsToDelete];
        for (int i = 0; i < numberOfPersonsToDelete; i++) {
            Person personToDelete = lastShownList.get(startIndex.getZeroBased() + i);
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
    public Set<Index> getTargetIndicesAsSet() {
        Set<Index> targetIndices = new java.util.HashSet<>();
        for (int i = startIndex.getZeroBased(); i <= endIndex.getZeroBased(); i++) {
            targetIndices.add(Index.fromZeroBased(i));
        }
        return targetIndices;
    }
}
