package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * Accepts either:
     * - prefixed form: n/KEYWORD [MORE_KEYWORDS]...
     * - legacy form: KEYWORD [MORE_KEYWORDS]...
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        List<String> keywords;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameArgs = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (nameArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            keywords = Arrays.asList(nameArgs.split("\\s+"));
        } else {
            keywords = Arrays.asList(trimmedArgs.split("\\s+"));
        }

        return new FindCommand(new NameContainsKeywordsPredicate(keywords));
    }

}
