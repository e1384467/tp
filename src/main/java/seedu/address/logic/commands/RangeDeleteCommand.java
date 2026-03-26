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
    private Person[] deletedPersons;
    private boolean wasExecuted = false;

    public RangeDeleteCommand(Index startIndex, Index endIndex) {
        this(startIndex, endIndex, Set.of());
    }

    /**
     * Creates a RangeDeleteCommand to delete the specified people.
     *
     * @param startIndex The starting index of the range of people to delete.
     * @param endIndex The ending index of the range of people to delete.
     * @param prefixes The prefixes indicating which fields to delete (if any).
     */
    public RangeDeleteCommand(Index startIndex, Index endIndex, Set<Prefix> prefixes) {
        super(prefixes);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.getErrorMessageForNoPersons(COMMAND_WORD));
        }

        verifyValidIndices(model, lastShownList);

        Person[] personsToDelete = getPersonsToDelete(lastShownList);
        StringBuilder deletedPersonsString = new StringBuilder();

        if (!getPrefixes().isEmpty()) {
            Person[] updatedPersons = getUpdatedPersons(personsToDelete);
            for (int i = 0; i < personsToDelete.length; i++) {
                Person person = personsToDelete[i];
                Person updatedPerson = updatedPersons[i];
                model.setPerson(person, updatedPerson);
                deletedPersonsString.append("\n" + Messages.format(updatedPerson));
            }
            wasExecuted = true;
            return new CommandResult(String.format(MESSAGE_DELETE_FIELD_SUCCESS, deletedPersonsString));
        }

        for (Person person : personsToDelete) {
            model.deletePerson(person);
            deletedPersonsString.append("\n" + Messages.format(person));
        }
        wasExecuted = true;
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersonsString));
    }

    private void verifyValidIndices(Model model, List<Person> lastShownList) throws CommandException {
        if (startIndex.getZeroBased() >= lastShownList.size() || endIndex.getZeroBased() >= lastShownList.size()) {
            Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
            throw new CommandException(Messages.getErrorMessageForInvalidIndices(lastIndex));
        }

        if (startIndex.getZeroBased() > endIndex.getZeroBased()) {
            throw new CommandException(Messages.getErrorMessageForInvalidRangeIndices());
        }
    }

    private Person[] getPersonsToDelete(List<Person> lastShownList) {
        int numberOfPersonsToDelete = endIndex.getZeroBased() - startIndex.getZeroBased() + 1;
        Person[] personsToDelete = new Person[numberOfPersonsToDelete];
        for (int i = 0; i < numberOfPersonsToDelete; i++) {
            Person personToDelete = lastShownList.get(startIndex.getZeroBased() + i);
            personsToDelete[i] = personToDelete;
        }

        deletedPersons = personsToDelete;
        return personsToDelete;
    }

    @Override
    public void undo(Model model) throws CommandException {
        requireNonNull(model);
        if (wasExecuted && deletedPersons != null) {
            for (Person person : deletedPersons) {
                model.addPerson(person);
            }
        }
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
