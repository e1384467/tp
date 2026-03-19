package seedu.address.logic.parser;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ExitCommand} object.
 * <p>
 * The exit command does not accept any parameters. If any additional
 * arguments are provided, a {@link ParseException} will be thrown.
 */
public class ExitCommandParser implements Parser<ExitCommand> {

    /**
     * Parses the given {@code String} of arguments and returns an {@code ExitCommand}.
     *
     * @param arguments The input arguments provided by the user after the command word.
     * @return An {@code ExitCommand} object.
     * @throws ParseException If additional parameters are detected.
     */
    @Override
    public ExitCommand parse(String arguments) throws ParseException {
        if (!arguments.trim().isEmpty()) {
            throw new ParseException(ExitCommand.MESSAGE_EXTRA_PARAMETERS);
        }
        return new ExitCommand();
    }
}
