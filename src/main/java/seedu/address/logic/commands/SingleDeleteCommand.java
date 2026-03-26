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
 * Deletes a person identified using its displayed index from the address book.
 */
public class SingleDeleteCommand extends DeleteCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;
    private Person deletedPerson;
    private boolean wasExecuted = false;

    public SingleDeleteCommand(Index targetIndex) {
        this(targetIndex, Set.of());
    }

    /**
     * Creates a SingleDeleteCommand to delete the specified person.
     *
     * @param targetIndex The index of the person to delete.
     * @param prefixes The prefixes indicating which fields to delete (if any).
     */
    public SingleDeleteCommand(Index targetIndex, Set<Prefix> prefixes) {
        super(prefixes);
        this.targetIndex = targetIndex;
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

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
            throw new CommandException(Messages.getErrorMessageForInvalidIndex(lastIndex));
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        deletedPerson = personToDelete;

        if (!getPrefixes().isEmpty()) {
            Person updatedPerson = getUpdatedPerson(personToDelete);
            assert updatedPerson.isSamePerson(personToDelete)
                    : "Updated person should be have the same identity as original person.";
            model.setPerson(personToDelete, updatedPerson);
            return new CommandResult(String.format(MESSAGE_DELETE_FIELD_SUCCESS, Messages.format(updatedPerson)));
        }

        model.deletePerson(personToDelete);
        wasExecuted = true;
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public void undo(Model model) throws CommandException {
        requireNonNull(model);
        if (wasExecuted && deletedPerson != null) {
            model.addPerson(deletedPerson);
        }
    }

    @Override
    public Set<Index> getTargetIndicesAsSet() {
        return Set.of(targetIndex);
    }
}
