package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate, "Patient Name: first");
        FindCommand findSecondCommand = new FindCommand(secondPredicate, "Patient Name: second");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, "Patient Name: first");
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate, "Patient Name: ");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model,
                "Found 0 patient(s) whose identifiers include the following criteria: Patient Name: ", expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, "Patient Name: Kurz Elle Kunz");
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = "Found 3 patient(s) whose identifiers include the following criteria: "
                + "Patient Name: Kurz Elle Kunz";
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // FIX: The order must match your Urgency sorting (High -> Low)
        // If FIONA has higher urgency than ELLE, and ELLE higher than CARL:
        assertEquals(Arrays.asList(ELLE, FIONA, CARL), model.getFilteredPersonList());
    }

    @Test
    public void execute_nullCriteriaDescription_usesHeaderOnly() {
        // Reuse existing predicate that matches 3 persons
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate, null);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model,
                "Found 3 patient(s) whose identifiers include the following criteria: ", expectedModel);
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate, "Patient Name: keyword");
        String result = findCommand.toString();
        assertTrue(result.contains(FindCommand.class.getCanonicalName()));
        assertTrue(result.contains("predicate=" + predicate));
        assertTrue(result.contains("criteriaDescription=Patient Name: keyword"));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
