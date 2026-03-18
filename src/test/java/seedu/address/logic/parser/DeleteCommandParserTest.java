package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.MultipleDeleteCommand;
import seedu.address.logic.commands.RangeDeleteCommand;
import seedu.address.logic.commands.SingleDeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgsSingleIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new SingleDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsSingleIndexWithWhitespace_returnsDeleteCommand() {
        assertParseSuccess(parser, "  1  ", new SingleDeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgsSingleIndex_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SingleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsSingleIndexZero_throwsParseException() {
        assertParseFailure(parser, "0", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, SingleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsMultipleIndices_returnsDeleteCommand() {
        assertParseSuccess(parser, "1,2,3",
                new MultipleDeleteCommand(new Index[]{ INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON }));
    }

    @Test
    public void parse_validArgsMultipleIndicesWithWhitespace_returnsDeleteCommand() {
        assertParseSuccess(parser, "  1,2,3  ",
                new MultipleDeleteCommand(new Index[]{ INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON }));
    }

    @Test
    public void parse_invalidArgsMultipleIndices_throwsParseException() {
        assertParseFailure(parser, "1,a,3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsMultipleIndicesZero_throwsParseException() {
        assertParseFailure(parser, "0,1,2", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsMultipleIndicesWithSpaceBeforeDelimiter_throwsParseException() {
        assertParseFailure(parser, "1 ,2 ,3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsMultipleIndicesWithSpaceAfterDelimiter_throwsParseException() {
        assertParseFailure(parser, "1, 2, 3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsRangeIndices_returnsDeleteCommand() {
        assertParseSuccess(parser, "1-3",
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_validArgsRangeIndicesWithWhitespace_returnsDeleteCommand() {
        assertParseSuccess(parser, "  1-3  ",
                new RangeDeleteCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON));
    }

    @Test
    public void parse_invalidArgsRangeIndices_throwsParseException() {
        assertParseFailure(parser, "1-a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RangeDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsRangeIndicesZero_throwsParseException() {
        assertParseFailure(parser, "0-2", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RangeDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsRangeIndicesWithSpaceBeforeDelimiter_throwsParseException() {
        assertParseFailure(parser, "1 -3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RangeDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsRangeIndicesWithSpaceAfterDelimiter_throwsParseException() {
        assertParseFailure(parser, "1- 3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RangeDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgsMultipleIndicesAndRangeIndices_throwsParseException() {
        assertParseFailure(parser, "1,2-3", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(
                MESSAGE_MISSING_PERSON_INDEX, DeleteCommand.MESSAGE_USAGE));
    }
}
