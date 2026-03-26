package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showPersonsInIndexRange;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
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
 * {@code RangeDeleteCommand}.
 */
public class RangeDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndicesUnfilteredList_success() {
        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person thirdPersonToDelete = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                "\n" + Messages.format(firstPersonToDelete) + "\n" + Messages.format(secondPersonToDelete)
                        + "\n" + Messages.format(thirdPersonToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);
        expectedModel.deletePerson(thirdPersonToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForInvalidIndices(lastIndex));
    }

    @Test
    public void execute_validIndicesFilteredList_success() {
        showPersonsInIndexRange(model, INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        Person firstPersonToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person thirdPersonToDelete = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                "\n" + Messages.format(firstPersonToDelete) + "\n" + Messages.format(secondPersonToDelete)
                        + "\n" + Messages.format(thirdPersonToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPersonToDelete);
        expectedModel.deletePerson(secondPersonToDelete);
        expectedModel.deletePerson(thirdPersonToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonsInIndexRange(model, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        Index outOfBoundIndex = INDEX_THIRD_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, outOfBoundIndex);

        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForInvalidIndices(lastIndex));
    }

    @Test
    public void execute_startIndexEqualsEndIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                "\n" + Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_startIndexEqualsEndIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                "\n" + Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_startIndexGreaterThanEndIndexUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);

        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForInvalidRangeIndices());
    }

    @Test
    public void execute_startIndexGreaterThanEndIndexFilteredList_throwsCommandException() {
        showPersonsInIndexRange(model, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);

        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForInvalidRangeIndices());
    }

    @Test
    public void execute_validPrefixUnfilteredList_success() {
        Person firstTargetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondTargetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person thirdTargetPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        assertFalse(firstTargetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes.");
        assertFalse(secondTargetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes.");
        assertFalse(thirdTargetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes.");

        DeleteCommand deleteCommand =
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON, Set.of(PREFIX_NOTES));

        Person firstExpectedPerson = new PersonBuilder(firstTargetPerson).withNotes("").build();
        Person secondExpectedPerson = new PersonBuilder(secondTargetPerson).withNotes("").build();
        Person thirdExpectedPerson = new PersonBuilder(thirdTargetPerson).withNotes("").build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FIELD_SUCCESS,
                "\n" + Messages.format(firstExpectedPerson) + "\n" + Messages.format(secondExpectedPerson)
                        + "\n" + Messages.format(thirdExpectedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstTargetPerson, firstExpectedPerson);
        expectedModel.setPerson(secondTargetPerson, secondExpectedPerson);
        expectedModel.setPerson(thirdTargetPerson, thirdExpectedPerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingFieldValueUnfilteredList_throwsCommandException() {
        Person secondTargetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertTrue(secondTargetPerson.getSymptoms().isEmpty(),
                "Precondition failed: target person should not have symptoms.");

        DeleteCommand deleteCommand =
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON, Set.of(PREFIX_SYMPTOM));

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_VALUE_FOR_PERSON);
    }

    @Test
    public void execute_validPrefixFilteredList_success() {
        showPersonsInIndexRange(model, INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        Person firstTargetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondTargetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person thirdTargetPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        assertFalse(firstTargetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes.");
        assertFalse(secondTargetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes.");
        assertFalse(thirdTargetPerson.getNotes().getValue().isEmpty(),
                "Precondition failed: target person should have notes.");

        DeleteCommand deleteCommand =
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON, Set.of(PREFIX_NOTES));

        Person firstExpectedPerson = new PersonBuilder(firstTargetPerson).withNotes("").build();
        Person secondExpectedPerson = new PersonBuilder(secondTargetPerson).withNotes("").build();
        Person thirdExpectedPerson = new PersonBuilder(thirdTargetPerson).withNotes("").build();
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_FIELD_SUCCESS,
                "\n" + Messages.format(firstExpectedPerson) + "\n" + Messages.format(secondExpectedPerson)
                        + "\n" + Messages.format(thirdExpectedPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstTargetPerson, firstExpectedPerson);
        expectedModel.setPerson(secondTargetPerson, secondExpectedPerson);
        expectedModel.setPerson(thirdTargetPerson, thirdExpectedPerson);
        showPersonsInIndexRange(expectedModel, INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_missingFieldValueFilteredList_throwsCommandException() {
        showPersonsInIndexRange(model, INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);

        Person secondTargetPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        assertTrue(secondTargetPerson.getSymptoms().isEmpty(),
                "Precondition failed: target person should not have symptoms.");

        DeleteCommand deleteCommand =
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON, Set.of(PREFIX_SYMPTOM));

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_NO_VALUE_FOR_PERSON);
    }

    @Test
    public void execute_noPersons_throwsCommandException() {
        showNoPerson(model);

        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertCommandFailure(deleteCommand, model, Messages.getErrorMessageForNoPersons(DeleteCommand.COMMAND_WORD));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand =
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        DeleteCommand deleteSecondCommand =
                new RangeDeleteCommand(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy =
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different people -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different target indices and different command type -> returns false
        DeleteCommand deleteMultipleCommand =
                new MultipleDeleteCommand(new Index[]{ INDEX_FIRST_PERSON, INDEX_THIRD_PERSON });
        assertFalse(deleteFirstCommand.equals(deleteMultipleCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new RangeDeleteCommand(targetIndex, targetIndex, Set.of(PREFIX_NOTES));
        String expected = RangeDeleteCommand.class.getCanonicalName()
                + "{targetIndices=" + Set.of(targetIndex)
                + ", prefixes=" + Set.of(PREFIX_NOTES) + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void isUndoable_returnsTrue() {
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        assertTrue(deleteCommand.isUndoable());
    }

    @Test
    public void undo_personsWereDeleted_successful() throws Exception {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person firstPersonToDelete = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPersonToDelete = testModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person thirdPersonToDelete = testModel.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        int initialSize = testModel.getAddressBook().getPersonList().size();

        // Execute delete
        DeleteCommand deleteCommand = new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        deleteCommand.execute(testModel);
        assertEquals(initialSize - 3, testModel.getAddressBook().getPersonList().size());

        // Execute undo
        deleteCommand.undo(testModel);

        // Verify the persons were restored
        assertEquals(initialSize, testModel.getAddressBook().getPersonList().size());
        assertTrue(testModel.getAddressBook().getPersonList().contains(firstPersonToDelete));
        assertTrue(testModel.getAddressBook().getPersonList().contains(secondPersonToDelete));
        assertTrue(testModel.getAddressBook().getPersonList().contains(thirdPersonToDelete));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
