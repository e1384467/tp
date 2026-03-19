package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_OF_KIN_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URGENCY;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKinPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;
import seedu.address.model.symptom.Symptom;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_PATIENT_NAME,
                        PREFIX_PATIENT_PHONE,
                        PREFIX_EMAIL,
                        PREFIX_ADDRESS,
                        PREFIX_SYMPTOM,
                        PREFIX_IC,
                        PREFIX_URGENCY,
                        PREFIX_NEXT_OF_KIN_PHONE,
                        PREFIX_DOCTOR
                );

        if (!arePrefixesPresent(argMultimap,
                PREFIX_PATIENT_NAME,
                PREFIX_ADDRESS,
                PREFIX_PATIENT_PHONE,
                PREFIX_EMAIL,
                PREFIX_IC,
                PREFIX_URGENCY,
                PREFIX_NEXT_OF_KIN_PHONE,
                PREFIX_DOCTOR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PATIENT_NAME,
                PREFIX_PATIENT_PHONE,
                PREFIX_EMAIL,
                PREFIX_ADDRESS,
                PREFIX_IC,
                PREFIX_URGENCY,
                PREFIX_NEXT_OF_KIN_PHONE,
                PREFIX_DOCTOR);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_PATIENT_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PATIENT_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Symptom> symptomList = ParserUtil.parseSymptoms(argMultimap.getAllValues(PREFIX_SYMPTOM));
        Ic ic = ParserUtil.parseIc(argMultimap.getValue(PREFIX_IC).get());
        UrgencyLevel urgencyLevel = ParserUtil.parseUrgencyLevel(argMultimap.getValue(PREFIX_URGENCY).get());
        NextOfKinPhone nextOfKinPhone = ParserUtil.parseNextOfKinPhone(argMultimap.getValue(PREFIX_NEXT_OF_KIN_PHONE)
                .get());
        DoctorName doctorName = ParserUtil.parseDoctorName(argMultimap.getValue(PREFIX_DOCTOR).get());


        Person person = new Person(name, phone, email, address, symptomList, ic,
                urgencyLevel, nextOfKinPhone, doctorName);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
