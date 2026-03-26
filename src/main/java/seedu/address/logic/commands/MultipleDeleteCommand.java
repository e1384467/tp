package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.getErrorMessageForDuplicateIndices;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes multiple people identified using their displayed index from the address book.
 */
public class MultipleDeleteCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the people identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX,INDEX[,INDEX,...] (must be unique positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1,2,4";

    private final Index[] targetIndices;
    private Person[] deletedPersons;
    private boolean wasExecuted = false;

    public MultipleDeleteCommand(Index... targetIndices) {
        this(targetIndices, Set.of());
    }

    /**
     * Creates a MultipleDeleteCommand to delete the specified people.
     *
     * @param targetIndices The indices of the people to delete.
     * @param prefixes The prefixes indicating which fields to delete (if any).
     */
    public MultipleDeleteCommand(Index[] targetIndices, Set<Prefix> prefixes) {
        super(prefixes);
        this.targetIndices = targetIndices;
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

        verifyNoDuplicateIndices();

        Person[] personsToDelete = getPersonsToDelete(model, lastShownList);
        StringBuilder deletedPersonsString = new StringBuilder();

        if (!getPrefixes().isEmpty()) {
            Person[] updatedPersons = getUpdatedPersons(personsToDelete);
            for (int i = 0; i < personsToDelete.length; i++) {
                Person person = personsToDelete[i];
                Person updatedPerson = updatedPersons[i];
                model.setPerson(person, updatedPerson);
                deletedPersonsString.append("\n" + Messages.format(updatedPerson));
            }
            return new CommandResult(String.format(MESSAGE_DELETE_FIELD_SUCCESS, deletedPersonsString));
        }

        deletedPersons = personsToDelete;

        for (Person person : personsToDelete) {
            model.deletePerson(person);
            deletedPersonsString.append("\n" + Messages.format(person));
        }
        wasExecuted = true;
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersonsString));
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

    private void verifyNoDuplicateIndices() throws CommandException {
        Set<Index> seenIndices = new HashSet<>();
        Set<Index> duplicateIndices = new HashSet<>();
        for (Index index : targetIndices) {
            if (!seenIndices.add(index)) {
                duplicateIndices.add(index);
            }
        }

        if (!duplicateIndices.isEmpty()) {
            throw new CommandException(getErrorMessageForDuplicateIndices(duplicateIndices));
        }
    }

    private Person[] getPersonsToDelete(Model model, List<Person> lastShownList) throws CommandException {
        Person[] personsToDelete = new Person[targetIndices.length];
        for (int i = 0; i < targetIndices.length; i++) {
            Index targetIndex = targetIndices[i];
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
                throw new CommandException(Messages.getErrorMessageForInvalidIndices(lastIndex));
            }

            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            personsToDelete[i] = personToDelete;
        }
        return personsToDelete;
    }

    @Override
    public Set<Index> getTargetIndicesAsSet() {
        return Set.of(targetIndices);
    }
}
