package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SYMPTOM_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateCommand.
 */
public class UpdateCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(editedPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(UpdateCommand
                .MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withSymptoms(VALID_SYMPTOM_HUSBAND).build();

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSymptoms(VALID_SYMPTOM_HUSBAND).build();
        UpdateCommand updateCommand = new UpdateCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(UpdateCommand
                .MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, new UpdatePersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(UpdateCommand
                .MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(UpdateCommand
                .MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(firstPerson).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON,
                new UpdatePersonDescriptorBuilder(personInList).build());

        assertCommandFailure(updateCommand, model, UpdateCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        UpdateCommand updateCommand = new UpdateCommand(outOfBoundIndex,
                new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(updateCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_appendNoteToExistingNote_success() throws Exception {
        // Covers lines 144-145: Testing the "else" block (adding to existing text)
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String originalNote = personToUpdate.getNotes().toString();
        String textToAppend = "More info";

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setNotesToAppend(new Notes(textToAppend));
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedNote = originalNote + "\n" + textToAppend;
        updateCommand.execute(model);

        assertEquals(expectedNote, model.getFilteredPersonList().get(0).getNotes().toString());
    }

    @Test
    public void execute_appendNoteToEmptyNote_success() throws Exception {
        // Covers line 142: Testing the case where the patient currently has no notes ("-" or empty)
        // First, clear the note for the first person
        model.setPerson(model.getFilteredPersonList().get(0),
                new PersonBuilder(model.getFilteredPersonList().get(0)).withNotes("").build());

        String textToAppend = "First Note";
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setNotesToAppend(new Notes(textToAppend));
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        updateCommand.execute(model);

        // Should NOT have a leading newline since original was empty
        assertEquals(textToAppend, model.getFilteredPersonList().get(0).getNotes().toString());
    }

    @Test
    public void execute_appendNoteExceedsLimit_throwsCommandException() {
        // 1. Set up two valid strings that, when combined, exceed your character limit (assuming a 500 char limit)
        String existingText = "a".repeat(250);
        String textToAppend = "b".repeat(251); // Combined length: 501

        // 2. Give the target patient the initial 250-character note
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithExistingNotes = new PersonBuilder(firstPerson).withNotes(existingText).build();
        model.setPerson(firstPerson, personWithExistingNotes);

        // 3. Set up the descriptor to append the 251-character note
        // This no longer crashes the test setup because 251 is a valid length on its own!
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setNotesToAppend(new Notes(textToAppend));

        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        // 4. Verify that the execution properly catches the overflow and throws the CommandException
        String expectedMessage = "Appending this text exceeds the note character constraints. "
                + Notes.MESSAGE_CONSTRAINTS;

        assertCommandFailure(updateCommand, model, expectedMessage);
    }

    @Test
    public void execute_appendNoteToNoteWithExistingContent_success() {
        // Covers the "False" branch of line 142 (existingNotesText is NOT empty)
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // Ensure the person definitely has existing notes
        Person personWithNotes = new PersonBuilder(personToUpdate).withNotes("Initial Note").build();
        model.setPerson(personToUpdate, personWithNotes);

        String textToAppend = "Second Note";
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withNotesToAppend(textToAppend).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        // This forces the "else" block (line 144) to run: combinedText = existing + "\n" + append
        String expectedNote = "Initial Note" + "\n" + textToAppend;
        Person editedPerson = new PersonBuilder(personWithNotes).withNotes(expectedNote).build();

        String expectedMessage = String.format(UpdateCommand.MESSAGE_UPDATE_PERSON_SUCCESS,
                Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personWithNotes, editedPerson);

        assertCommandSuccess(updateCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appendNonEmptyNote_success() throws Exception {
        // 1. Pick a person who already has some notes
        Person personToUpdate = model.getFilteredPersonList().get(0);

        // 2. Create a descriptor with a REAL (non-empty) note to append
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();
        descriptor.setNotesToAppend(new Notes("Patient is recovering well."));

        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        // 3. Execute the command
        updateCommand.execute(model);

        // 4. Verify the change
        String updatedNote = model.getFilteredPersonList().get(0).getNotes().toString();
        assertTrue(updatedNote.contains("Patient is recovering well."));
    }

    @Test
    public void equals() {
        final UpdateCommand standardCommand = new UpdateCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        UpdatePersonDescriptor copyDescriptor = new UpdatePersonDescriptor(DESC_AMY);
        UpdateCommand commandWithSameValues = new UpdateCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void isUndoable_returnsTrue() {
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, DESC_AMY);
        assertTrue(updateCommand.isUndoable());
    }

    @Test
    public void undo_personWasUpdated_successful() throws Exception {
        Model testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person originalPerson = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);

        // Execute update
        updateCommand.execute(testModel);
        Person updatedPerson = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertFalse(updatedPerson.getName().equals(originalPerson.getName()));

        // Execute undo
        updateCommand.undo(testModel);

        // Verify the person was reverted
        Person revertedPerson = testModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(originalPerson.getName(), revertedPerson.getName());
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        UpdatePersonDescriptor editPersonDescriptor = new UpdatePersonDescriptor();
        UpdateCommand updateCommand = new UpdateCommand(index, editPersonDescriptor);
        String expected = UpdateCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, updateCommand.toString());
    }

}
