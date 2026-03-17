package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * Accepts either:
     * - prefixed form: n/KEYWORD [MORE_KEYWORDS]..., ic/IC_NUMBER, p/PHONE_NUMBER
     * - legacy form: KEYWORD [MORE_KEYWORDS]... (no prefix used)
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        // No text at all or only whitespace after the command word
        if (trimmedArgs.isEmpty()) {
            throw new ParseException("At least one parameter to search with must be provided. You "
                + "can use the command 'find' with the following parameters: n/NAME, ic/IC_NUMBER, p/PHONE_NUMBER");
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_IC, PREFIX_PHONE);

        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasIc = argMultimap.getValue(PREFIX_IC).isPresent();
        boolean hasPhone = argMultimap.getValue(PREFIX_PHONE).isPresent();

        // Disallow duplicate prefixes.
        if (argMultimap.getAllValues(PREFIX_NAME).size() > 1
                || argMultimap.getAllValues(PREFIX_IC).size() > 1
                || argMultimap.getAllValues(PREFIX_PHONE).size() > 1) {
            throw new ParseException(
                    "Duplicate parameter detected. Each prefix (e.g., p/, ic/) should only be used once.");
        }

        // Legacy behaviour: no prefixes, treat entire args as name keywords
        if (!hasName && !hasIc && !hasPhone) {
            List<String> legacyKeywords = Arrays.asList(trimmedArgs.split("\\s+"));
            String criteriaDescription = "Patient Name: " + trimmedArgs;
            return new FindCommand(new NameContainsKeywordsPredicate(legacyKeywords), criteriaDescription);
        }

        Predicate<Person> predicate = person -> false;
        StringBuilder criteriaBuilder = new StringBuilder();

        if (hasName) {
            String nameArgs = argMultimap.getValue(PREFIX_NAME).get().trim();
            if (nameArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            List<String> nameKeywords = Arrays.asList(nameArgs.split("\\s+"));
            predicate = predicate.or(new NameContainsKeywordsPredicate(nameKeywords));
            criteriaBuilder.append("Patient Name: ").append(nameArgs);
        }

        if (hasIc) {
            String icArg = argMultimap.getValue(PREFIX_IC).get().trim();
            if (icArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String icToMatch = icArg;
            predicate = predicate.or(person -> person.getIc().value.equalsIgnoreCase(icToMatch));
            if (criteriaBuilder.length() > 0) {
                criteriaBuilder.append(", ");
            }
            criteriaBuilder.append("IC Number: ").append(icArg);
        }

        if (hasPhone) {
            String phoneArg = argMultimap.getValue(PREFIX_PHONE).get().trim();
            if (phoneArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String phoneToMatch = phoneArg;
            predicate = predicate.or(person -> person.getPhone().value.equals(phoneToMatch));
            if (criteriaBuilder.length() > 0) {
                criteriaBuilder.append(", ");
            }
            criteriaBuilder.append("Phone Number: ").append(phoneArg);
        }

        String criteriaDescription = criteriaBuilder.toString().trim();
        return new FindCommand(predicate, criteriaDescription);
    }

}
