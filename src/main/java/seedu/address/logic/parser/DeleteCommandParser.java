package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.MultipleDeleteCommand;
import seedu.address.logic.commands.RangeDeleteCommand;
import seedu.address.logic.commands.SingleDeleteCommand;
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
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SYMPTOM, PREFIX_NOTES);

        final String indicesString = argMultimap.getPreamble();
        if (indicesString.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_MISSING_PERSON_INDEX, DeleteCommand.MESSAGE_USAGE));
        }
        Set<Prefix> prefixes = argMultimap.getPrefixes();

        if (indicesString.contains(MULTIPLE_INDICES_DELIMITER)) {
            return parseMultipleIndices(indicesString, prefixes);
        } else if (indicesString.contains(RANGE_INDICES_DELIMITER)) {
            return parseRangeIndices(indicesString, prefixes);
        } else {
            return parseSingleIndex(indicesString, prefixes);
        }
    }

    private DeleteCommand parseSingleIndex(String indexString, Set<Prefix> prefixes) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SingleDeleteCommand.MESSAGE_USAGE), pe);
        }

        return new SingleDeleteCommand(index, prefixes);
    }

    private DeleteCommand parseMultipleIndices(String indicesString, Set<Prefix> prefixes) throws ParseException {
        final Matcher matcher = MULTIPLE_INDICES_ARGUMENT_FORMAT.matcher(indicesString.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE));
        }

        String[] indexStrings = matcher.group("indices").split(MULTIPLE_INDICES_DELIMITER);
        Index[] indices = new Index[indexStrings.length];
        for (int i = 0; i < indexStrings.length; i++) {
            try {
                indices[i] = ParserUtil.parseIndex(indexStrings[i].trim());
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, MultipleDeleteCommand.MESSAGE_USAGE), pe);
            }
        }

        return new MultipleDeleteCommand(indices, prefixes);
    }

    private DeleteCommand parseRangeIndices(String indicesString, Set<Prefix> prefixes) throws ParseException {
        final Matcher matcher = RANGE_ARGUMENT_FORMAT.matcher(indicesString.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RangeDeleteCommand.MESSAGE_USAGE));
        }

        Index startIndex;
        Index endIndex;
        try {
            startIndex = ParserUtil.parseIndex(matcher.group("start").trim());
            endIndex = ParserUtil.parseIndex(matcher.group("end").trim());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RangeDeleteCommand.MESSAGE_USAGE), pe);
        }

        return new RangeDeleteCommand(startIndex, endIndex, prefixes);
    }
}
