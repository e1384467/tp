package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                "At least one parameter to search with must be provided. You can use the command 'find'"
                    + " with the following parameters: n/NAME, ic/IC_NUMBER, p/PHONE_NUMBER");
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                        "Patient Name: Alice Bob");
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validPrefixedArgs_doesNotThrow() {
        // simple prefixed form
        assertDoesNotThrow(() -> parser.parse(" n/Alice Bob"));

        // prefixed form with surrounding whitespace
        assertDoesNotThrow(() -> parser.parse(" \n n/Alice \n \t Bob  \t"));
    }

    @Test
    public void parse_emptyPrefixedArgs_throwsParseException() {
        assertParseFailure(parser, " n/   ",
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
                    + " with the following parameters: n/NAME, ic/IC_NUMBER, p/PHONE_NUMBER");
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " n/Alice n/Bob",
                "Duplicate parameter detected. Each prefix (e.g., p/, ic/) should only be used once.");
        assertParseFailure(parser, " ic/S1234567A ic/S7654321B",
                "Duplicate parameter detected. Each prefix (e.g., p/, ic/) should only be used once.");
        assertParseFailure(parser, " p/91234567 p/98765432",
                "Duplicate parameter detected. Each prefix (e.g., p/, ic/) should only be used once.");
    }

    @Test
    public void parse_multiplePrefixes_doesNotThrow() {
        // name + ic + phone together to test comma joining output.
        assertDoesNotThrow(() -> parser.parse(" n/Alice ic/S1234567A p/91234567"));
    }

}
