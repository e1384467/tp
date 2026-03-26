package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UndoCommand;

/**
 * Contains unit tests for {@code UndoCommandParser}.
 */
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_noArguments_success() {
        UndoCommand command = parser.parse("");
        assert command != null;
    }

    @Test
    public void parse_emptyString_success() {
        UndoCommand command = parser.parse("   ");
        assert command != null;
    }

    @Test
    public void parse_anyArguments_success() {
        // UndoCommand ignores any arguments passed and returns a valid UndoCommand
        UndoCommand command = parser.parse("some random text");
        assert command != null;
    }
}
