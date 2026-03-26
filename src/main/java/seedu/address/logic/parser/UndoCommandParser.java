package seedu.address.logic.parser;

import seedu.address.logic.commands.UndoCommand;

/**
 * Parses input arguments and creates a new UndoCommand object.
 */
public class UndoCommandParser implements Parser<UndoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UndoCommand
     * and returns an UndoCommand object for execution.
     * UndoCommand takes no arguments.
     *
     * @param args irrelevant (should be empty)
     * @return a new UndoCommand
     */
    @Override
    public UndoCommand parse(String args) {
        return new UndoCommand();
    }
}
