package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.SingleUpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class MultipleUpdateCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex);
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptor();

        MultipleUpdateCommand command = new MultipleUpdateCommand(indices, descriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multiplePersons_success() {
        // Target the first and second person
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        MultipleUpdateCommand command = new MultipleUpdateCommand(indices, descriptor);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        // Create the expected updated persons
        Person updatedFirstPerson = new PersonBuilder(firstPerson).withPhone(VALID_PHONE_BOB).build();
        Person updatedSecondPerson = new PersonBuilder(secondPerson).withPhone(VALID_PHONE_BOB).build();

        // Build the expected success message
        String expectedNames = updatedFirstPerson.getName() + ", " + updatedSecondPerson.getName();
        String expectedMessage = String.format(MultipleUpdateCommand.MESSAGE_UPDATE_MULTIPLE_SUCCESS, expectedNames);

        // Build the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, updatedFirstPerson);
        expectedModel.setPerson(secondPerson, updatedSecondPerson);

        // Asserts lines 54-57, 63-71 (Successful Loop)
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        // We will target the FIRST person
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON);

        // We will try to update the FIRST person to have the exact details of the SECOND person
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder(secondPerson).build();
        MultipleUpdateCommand command = new MultipleUpdateCommand(indices, descriptor);

        // The conflict happens on the first person, because they are the one being changed into a duplicate
        String expectedMessage = SingleUpdateCommand.MESSAGE_DUPLICATE_PERSON
                + " (Conflict at " + firstPerson.getName().fullName + ")";

        // Asserts lines 58-61 (Duplicate Exception check)
        assertCommandFailure(command, model, expectedMessage);
    }


    @Test
    public void equals() {
        List<Index> indices1 = Arrays.asList(INDEX_FIRST_PERSON);
        List<Index> indices2 = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        UpdatePersonDescriptor descriptor1 = new UpdatePersonDescriptor(DESC_AMY);
        UpdatePersonDescriptor descriptor2 = new UpdatePersonDescriptor(DESC_BOB);

        MultipleUpdateCommand command1 = new MultipleUpdateCommand(indices1, descriptor1);
        MultipleUpdateCommand command2 = new MultipleUpdateCommand(indices2, descriptor2);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        MultipleUpdateCommand command1Copy = new MultipleUpdateCommand(indices1, descriptor1);
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different indices -> returns false
        assertFalse(command1.equals(command2));

        // different descriptor -> returns false
        MultipleUpdateCommand command3 = new MultipleUpdateCommand(indices1, descriptor2);
        assertFalse(command1.equals(command3));
    }

    @Test
    public void toStringMethod() {
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        UpdatePersonDescriptor updatePersonDescriptor = new UpdatePersonDescriptor();
        MultipleUpdateCommand command = new MultipleUpdateCommand(indices, updatePersonDescriptor);

        String expected = MultipleUpdateCommand.class.getCanonicalName() + "{targetIndices=" + indices
                + ", updatePersonDescriptor=" + updatePersonDescriptor + "}";
        assertEquals(expected, command.toString());
    }
}
