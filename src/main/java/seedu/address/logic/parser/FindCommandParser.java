package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;

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
                + "can use the command 'find' with the following parameters: pn/NAME, ic/IC_NUMBER, p/PHONE_NUMBER");
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PATIENT_NAME, PREFIX_IC, PREFIX_PATIENT_PHONE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PATIENT_NAME, PREFIX_IC, PREFIX_PATIENT_PHONE);

        boolean hasName = argMultimap.getValue(PREFIX_PATIENT_NAME).isPresent();
        boolean hasIc = argMultimap.getValue(PREFIX_IC).isPresent();
        boolean hasPhone = argMultimap.getValue(PREFIX_PATIENT_PHONE).isPresent();

        // Legacy behaviour: no prefixes, treat entire args as name keywords
        if (!hasName && !hasIc && !hasPhone) {
            List<String> legacyKeywords = Arrays.asList(trimmedArgs.split("\\s+"));
            String criteriaDescription = "Patient Name: " + trimmedArgs;
            return new FindCommand(new NameContainsKeywordsPredicate(legacyKeywords), criteriaDescription);
        }

        Predicate<Person> predicate = null;
        StringBuilder criteriaBuilder = new StringBuilder();

        if (argMultimap.getValue(PREFIX_PATIENT_NAME).isPresent()) {
            String nameArgs = argMultimap.getValue(PREFIX_PATIENT_NAME).get().trim();
            if (nameArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            List<String> nameKeywords = Arrays.asList(nameArgs.split("\\s+"));
            Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
            predicate = predicate == null ? namePredicate : predicate.or(namePredicate);
            criteriaBuilder.append("Patient Name: ").append(nameArgs);
        }

        if (hasIc) {
            String icArg = argMultimap.getValue(PREFIX_IC).get().trim();
            if (icArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String icToMatch = icArg;
            Predicate<Person> icPredicate = person -> person.getIc().value.equalsIgnoreCase(icToMatch);
            predicate = predicate == null ? icPredicate : predicate.or(icPredicate);
            if (criteriaBuilder.length() > 0) {
                criteriaBuilder.append(", ");
            }
            criteriaBuilder.append("IC Number: ").append(icArg);
        }

        if (hasPhone) {
            String phoneArg = argMultimap.getValue(PREFIX_PATIENT_PHONE).get().trim();
            if (phoneArg.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            String phoneToMatch = phoneArg;
            Predicate<Person> phonePredicate = person -> person.getPhone().value.equals(phoneToMatch);
            predicate = predicate == null ? phonePredicate : predicate.or(phonePredicate);
            if (criteriaBuilder.length() > 0) {
                criteriaBuilder.append(", ");
            }
            criteriaBuilder.append("Phone Number: ").append(phoneArg);
        }

        String criteriaDescription = criteriaBuilder.toString().trim();
        assert predicate != null;
        return new FindCommand(predicate, criteriaDescription);
    }

}
