package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.symptom.Symptom;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
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

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PATIENT_NAME,
                PREFIX_PATIENT_PHONE,
                PREFIX_EMAIL,
                PREFIX_ADDRESS,
                PREFIX_IC,
                PREFIX_URGENCY,
                PREFIX_NEXT_OF_KIN_PHONE,
                PREFIX_DOCTOR
        );

        UpdatePersonDescriptor updatePersonDescriptor = new UpdatePersonDescriptor();

        if (argMultimap.getValue(PREFIX_PATIENT_NAME).isPresent()) {
            updatePersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_PATIENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PATIENT_PHONE).isPresent()) {
            updatePersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PATIENT_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            updatePersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_URGENCY).isPresent()) {
            updatePersonDescriptor.setUrgencyLevel(ParserUtil.parseUrgencyLevel(argMultimap.getValue(PREFIX_URGENCY)
                    .get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            updatePersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_IC).isPresent()) {
            updatePersonDescriptor.setIc(ParserUtil.parseIc(argMultimap.getValue(PREFIX_IC).get()));
        }
        if (argMultimap.getValue(PREFIX_NEXT_OF_KIN_PHONE).isPresent()) {
            updatePersonDescriptor.setNextOfKinPhone(ParserUtil
                    .parseNextOfKinPhone(argMultimap.getValue(PREFIX_NEXT_OF_KIN_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_DOCTOR).isPresent()) {
            updatePersonDescriptor.setDoctorName(ParserUtil.parseDoctorName(argMultimap.getValue(PREFIX_DOCTOR).get()));
        }
        parseSymptomsForEdit(argMultimap.getAllValues(PREFIX_SYMPTOM)).ifPresent(updatePersonDescriptor::setSymptoms);

        if (!updatePersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(index, updatePersonDescriptor);
    }

    /**
     * Parses {@code Collection<String> symptoms} into a {@code Set<Symptom>} if {@code symptoms} is non-empty.
     * If {@code symptoms} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Symptom>} containing zero symptoms.
     */
    private Optional<Set<Symptom>> parseSymptomsForEdit(Collection<String> symptoms) throws ParseException {
        assert symptoms != null;

        if (symptoms.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> symptomSet = symptoms.size() == 1 && symptoms.contains("")
                ? Collections.emptySet() : symptoms;
        return Optional.of(ParserUtil.parseSymptoms(symptomSet));
    }

}
