package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    private FindCommand getExpectedNameFindCommand() {
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                "Patient Name: Alice Bob");
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                "At least one parameter to search with must be provided. You can use the command 'find'"
                    + " with the following parameters: pn/NAME, ic/IC_NUMBER, p/PHONE_NUMBER");
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = getExpectedNameFindCommand();
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPrefixedArgs_doesNotThrow() {
        FindCommand expectedFindCommand = getExpectedNameFindCommand();

        // simple prefixed form
        assertParseSuccess(parser, " pn/Alice Bob", expectedFindCommand);

        // prefixed form with surrounding whitespace
        assertParseSuccess(parser, " \n pn/Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_emptyPrefixedArgs_throwsParseException() {
        assertParseFailure(parser, " pn/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIcPrefix_doesNotThrow() {
        assertDoesNotThrow(() -> parser.parse(" ic/S1234567A"));
    }

    @Test
    public void parse_validPhonePrefix_doesNotThrow() {
        assertDoesNotThrow(() -> parser.parse(" p/91234567"));
    }

    @Test
    public void parse_emptyIcPrefix_throwsParseException() {
        assertParseFailure(parser, " ic/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPhonePrefix_throwsParseException() {
        assertParseFailure(parser, " p/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFieldsProvided_throwsParseException() {
        assertParseFailure(parser, "",
                "At least one parameter to search with must be provided. You can use the command 'find'"
                    + " with the following parameters: pn/NAME, ic/IC_NUMBER, p/PHONE_NUMBER");
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " pn/Alice pn/Bob",
                getErrorMessageForDuplicatePrefixes(PREFIX_PATIENT_NAME));
        assertParseFailure(parser, " ic/S1234567A ic/S7654321B",
                getErrorMessageForDuplicatePrefixes(PREFIX_IC));
        assertParseFailure(parser, " p/91234567 p/98765432",
                getErrorMessageForDuplicatePrefixes(PREFIX_PATIENT_PHONE));
    }

    @Test
    public void parse_multiplePrefixes_doesNotThrow() {
        // name + ic + phone together to test comma joining output.
        assertDoesNotThrow(() -> parser.parse(" pn/Alice ic/S1234567A p/91234567"));
    }

}
