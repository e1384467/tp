package seedu.address.logic.parser;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExitCommandParser implements Parser<ExitCommand>{

    @Override
    public ExitCommand parse(String arguments) throws ParseException {
        if (!arguments.trim().isEmpty()) {
            throw new ParseException(ExitCommand.MESSAGE_EXTRA_PARAMETERS);
        }
        return new ExitCommand();
    }
}
