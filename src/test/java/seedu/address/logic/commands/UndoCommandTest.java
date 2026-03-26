package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code UndoCommand}.
 */
public class UndoCommandTest {

    @Test
    public void execute_noLastCommand_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setLastCommand(null);

        Model model = new ModelManager();

        assertThrows(CommandException.class, UndoCommand.MESSAGE_FAILURE, () -> undoCommand.execute(model));
    }

    @Test
    public void execute_undoAdd_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToAdd = new PersonBuilder().withName("Charlie").build();

        // Execute add command
        AddCommand addCommand = new AddCommand(personToAdd);
        addCommand.execute(model);

        // Verify person was added
        assertEquals(getTypicalAddressBook().getPersonList().size() + 1, model.getAddressBook().getPersonList().size());

        // Now undo
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setLastCommand(addCommand);

        CommandResult result = undoCommand.execute(model);

        // Verify undo was successful
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(getTypicalAddressBook().getPersonList().size(), model.getAddressBook().getPersonList().size());
    }

    @Test
    public void execute_undoDelete_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int initialSize = model.getAddressBook().getPersonList().size();

        // Execute delete command
        DeleteCommand deleteCommand = new SingleDeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.execute(model);

        // Verify person was deleted
        assertEquals(initialSize - 1, model.getAddressBook().getPersonList().size());

        // Now undo
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setLastCommand(deleteCommand);

        CommandResult result = undoCommand.execute(model);

        // Verify undo was successful
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        assertEquals(initialSize, model.getAddressBook().getPersonList().size());
    }

    @Test
    public void execute_undoUpdate_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person originalPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdateCommand.UpdatePersonDescriptor();
        descriptor.setName(new PersonBuilder().build().getName());

        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, descriptor);
        updateCommand.execute(model);

        // Verify person was updated
        Person updatedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(descriptor.getName().orElse(null), updatedPerson.getName());

        // Now undo
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setLastCommand(updateCommand);

        CommandResult result = undoCommand.execute(model);

        // Verify undo was successful
        assertEquals(UndoCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
        Person revertedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(originalPerson.getName(), revertedPerson.getName());
    }

    @Test
    public void toString_returnsCommandWord() {
        UndoCommand undoCommand = new UndoCommand();
        assertEquals(UndoCommand.COMMAND_WORD, undoCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
