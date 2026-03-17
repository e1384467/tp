package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String MULTIPLE_INDICES_DELIMITER = ",";
    private static final String RANGE_INDICES_DELIMITER = "-";
    private static final Pattern MULTIPLE_INDICES_ARGUMENT_FORMAT =
            Pattern.compile("(?<indices>^[0-9]+(,[0-9]+)*$)");
    private static final Pattern RANGE_ARGUMENT_FORMAT =
            Pattern.compile("(?<start>^[0-9]+)-(?<end>[0-9]+$)");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        if (args.contains(MULTIPLE_INDICES_DELIMITER)) {
            return parseMultipleIndices(args);
        } else if (args.contains(RANGE_INDICES_DELIMITER)) {
            return parseRangeIndices(args);
        } else {
            return parseSingleIndex(args);
        }
    }

    private DeleteCommand parseSingleIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    private DeleteCommand parseMultipleIndices(String args) throws ParseException {
        Matcher matcher = MULTIPLE_INDICES_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_MULTIPLE_INDICES));
        }

        String[] indexStrings = matcher.group("indices").split(MULTIPLE_INDICES_DELIMITER);
        Index[] indices = new Index[indexStrings.length];
        for (int i = 0; i < indexStrings.length; i++) {
            try {
                indices[i] = ParserUtil.parseIndex(indexStrings[i].trim());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                DeleteCommand.MESSAGE_USAGE_MULTIPLE_INDICES), pe);
            }
        }
        return new DeleteCommand(indices);
    }

    private DeleteCommand parseRangeIndices(String args) throws ParseException {
        Matcher matcher = RANGE_ARGUMENT_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_RANGE_INDICES));
        }

        Index startIndex;
        Index endIndex;
        try {
            startIndex = ParserUtil.parseIndex(matcher.group("start").trim());
            endIndex = ParserUtil.parseIndex(matcher.group("end").trim());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_RANGE_INDICES), pe);
        }

        if (startIndex.getZeroBased() > endIndex.getZeroBased()) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_INDEX_RANGE);
        }

        return new DeleteCommand(startIndex, endIndex);
    }
}
