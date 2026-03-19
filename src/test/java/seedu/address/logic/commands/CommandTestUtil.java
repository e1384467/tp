package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URGENCY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_SYMPTOM_HUSBAND = "husband";
    public static final String VALID_SYMPTOM_FRIEND = "friend";
    public static final String VALID_IC_AMY = "S1212121A";
    public static final String VALID_IC_BOB = "S1313131B";
    public static final String VALID_URGENCY_LEVEL_AMY = "high";
    public static final String VALID_URGENCY_LEVEL_BOB = "low";

    public static final String NAME_DESC_AMY = " " + PREFIX_PATIENT_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_PATIENT_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PATIENT_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PATIENT_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String SYMPTOM_DESC_FRIEND = " " + PREFIX_SYMPTOM + VALID_SYMPTOM_FRIEND;
    public static final String SYMPTOM_DESC_HUSBAND = " " + PREFIX_SYMPTOM + VALID_SYMPTOM_HUSBAND;
    public static final String IC_DESC_AMY = " " + PREFIX_IC + VALID_IC_AMY;
    public static final String IC_DESC_BOB = " " + PREFIX_IC + VALID_IC_BOB;
    public static final String URGENCY_LEVEL_DESC_AMY = " " + PREFIX_URGENCY + VALID_URGENCY_LEVEL_AMY;
    public static final String URGENCY_LEVEL_DESC_BOB = " " + PREFIX_URGENCY + VALID_URGENCY_LEVEL_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_PATIENT_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PATIENT_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_SYMPTOM_DESC = " " + PREFIX_SYMPTOM + "hubby*"; // '*' not allowed in symptoms
    public static final String INVALID_IC_DESC = " " + PREFIX_IC + "S1234567"; // missing last character
    public static final String INVALID_URGENCY_LEVEL_DESC = " " + PREFIX_URGENCY + "urgent"; // invalid urgency level

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final UpdateCommand.UpdatePersonDescriptor DESC_AMY;
    public static final UpdateCommand.UpdatePersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withSymptoms(VALID_SYMPTOM_FRIEND)
                .withIc(VALID_IC_AMY)
                .withUrgencyLevel(VALID_URGENCY_LEVEL_AMY)
                .build();
        DESC_BOB = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withSymptoms(VALID_SYMPTOM_HUSBAND, VALID_SYMPTOM_FRIEND)
                .withIc(VALID_IC_BOB)
                .withUrgencyLevel(VALID_URGENCY_LEVEL_BOB)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Collections.singletonList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the persons in the given index range in the
     * {@code model}'s address book.
     */
    public static void showPersonsInIndexRange(Model model, Index startIndex, Index endIndex) {
        assertTrue(startIndex.getZeroBased() < model.getFilteredPersonList().size());
        assertTrue(endIndex.getZeroBased() < model.getFilteredPersonList().size());
        assertTrue(startIndex.getZeroBased() <= endIndex.getZeroBased());

        List<String> expectedKeywords = new ArrayList<>();
        for (int i = startIndex.getZeroBased(); i <= endIndex.getZeroBased(); i++) {
            Person person = model.getFilteredPersonList().get(i);
            final String[] splitName = person.getName().fullName.split("\\s+");
            expectedKeywords.add(splitName[0]);
        }

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(expectedKeywords));

        int expectedSize = endIndex.getZeroBased() - startIndex.getZeroBased() + 1;
        assertEquals(expectedSize, model.getFilteredPersonList().size());
    }
}
