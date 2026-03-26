package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code SingleDeleteCommand}.
 */
public class SingleDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new SingleDeleteCommand(outOfBoundIndex);

        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForInvalidIndex(lastIndex));
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new SingleDeleteCommand(outOfBoundIndex);

        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForInvalidIndex(lastIndex));
    }

    @Test
    public void execute_validPrefixesUnfilteredList_success() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertFalse(targetPerson.getSymptoms().isEmpty() || targetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have symptoms and notes.");
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON, Set.of(PREFIX_SYMPTOM, PREFIX_NOTES));

        Person expectedPerson = new PersonBuilder(targetPerson).withSymptoms().withNotes("").build();
        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_FIELD_SUCCESS, Messages.format(expectedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(targetPerson, expectedPerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingFieldValueUnfilteredList_throwsCommandException() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertTrue(targetPerson.getSymptoms().isEmpty() && !targetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes but no symptoms.");

        DeleteCommand deleteCommand =
                new SingleDeleteCommand(INDEX_SECOND_PERSON, Set.of(PREFIX_SYMPTOM, PREFIX_NOTES));

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_VALUE_FOR_PERSON);
    }

    @Test
    public void execute_validPrefixFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertFalse(targetPerson.getSymptoms().isEmpty(),
                "Precondition failed: target person should have symptoms.");
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON, Set.of(PREFIX_SYMPTOM));

        Person expectedPerson = new PersonBuilder(targetPerson).withSymptoms().build();
        String expectedMessage = String.format(
                DeleteCommand.MESSAGE_DELETE_FIELD_SUCCESS, Messages.format(expectedPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(targetPerson, expectedPerson);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingFieldValueFilteredList_throwsCommandException() {
        // Set the target person's notes to be empty to trigger the missing field value condition
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstPersonWithoutNotes = new PersonBuilder(firstPerson).withNotes("").build();
        model.setPerson(firstPerson, firstPersonWithoutNotes);

        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertTrue(targetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should not have notes.");

        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON, Set.of(PREFIX_NOTES));

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_VALUE_FOR_PERSON);
    }

    @Test
    public void execute_noPersons_throwsCommandException() {
        showNoPerson(model);

        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForNoPersons(DeleteCommand.COMMAND_WORD));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new SingleDeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommandWithPrefixes =
                new SingleDeleteCommand(INDEX_FIRST_PERSON, Set.of(PREFIX_SYMPTOM, PREFIX_NOTES));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new SingleDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // same target index but different command type -> returns true
        DeleteCommand deleteRangeCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteRangeCommand));

        // same target index and same prefixes -> returns true
        DeleteCommand deleteFirstCommandWithSamePrefixes =
                new SingleDeleteCommand(INDEX_FIRST_PERSON, Set.of(PREFIX_SYMPTOM, PREFIX_NOTES));
        assertTrue(deleteFirstCommandWithPrefixes.equals(deleteFirstCommandWithSamePrefixes));

        // same target index but different prefixes -> returns false
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandWithPrefixes));

        // same target indices and same prefixes but different command type -> returns true
        DeleteCommand deleteMultipleCommandWithSamePrefixes =
                new MultipleDeleteCommand(new Index[]{ INDEX_FIRST_PERSON }, Set.of(PREFIX_SYMPTOM, PREFIX_NOTES));
        assertTrue(deleteFirstCommandWithSamePrefixes.equals(deleteMultipleCommandWithSamePrefixes));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new SingleDeleteCommand(targetIndex, Set.of(PREFIX_SYMPTOM, PREFIX_NOTES));
        String expected = SingleDeleteCommand.class.getCanonicalName()
                + "{targetIndices=" + Set.of(targetIndex)
                + ", prefixes=" + Set.of(PREFIX_SYMPTOM, PREFIX_NOTES) + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void isUndoable_returnsTrue() {
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteCommand.isUndoable());
    }

    @Test
    public void undo_personWasDeleted_successful() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int initialSize = model.getAddressBook().getPersonList().size();

        // Execute delete
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.execute(model);
        assertEquals(initialSize - 1, model.getAddressBook().getPersonList().size());

        // Execute undo
        deleteCommand.undo(model);

        // Verify the person was restored
        assertEquals(initialSize, model.getAddressBook().getPersonList().size());
        assertTrue(model.getAddressBook().getPersonList().contains(personToDelete));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
