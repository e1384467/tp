package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_OUT_OF_RANGE_INDEX = "Please use an index within the range of 1 and %1$s";
    public static final String MESSAGE_INVALID_PERSON_INDICES = "One or more person indices provided are invalid";
    public static final String MESSAGE_INVALID_PERSON_INDEX_RANGE = "The person index range provided is invalid";
    public static final String MESSAGE_START_INDEX_GREATER_THAN_END_INDEX =
            "Start index cannot be greater than end index";
    public static final String MESSAGE_MISSING_PERSON_INDEX = "The index field cannot be empty. \n%1$s";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_PERSONS_SEARCHED = "Found %1$d patients matching the criteria  persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_DUPLICATE_INDICES =
            "Duplicated indices are not allowed. The following indices provided are duplicated: ";
    public static final String MESSAGE_NO_PERSONS = "There are no patient records to %1$s.";

    /**
     * Returns an error message indicating an out of range index and the valid person index range.
     */
    public static String getErrorMessageForInvalidIndex(Index rangeEndIndex) {
        return String.join(": ", MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                String.format(MESSAGE_OUT_OF_RANGE_INDEX, rangeEndIndex.getOneBased()));
    }

    /**
     * Returns an error message indicating out of range indices and the valid person index range.
     */
    public static String getErrorMessageForInvalidIndices(Index rangeEndIndex) {
        return String.join(": ", MESSAGE_INVALID_PERSON_INDICES,
                String.format(MESSAGE_OUT_OF_RANGE_INDEX, rangeEndIndex.getOneBased()));
    }

    /**
     * Returns an error message indicating start index greater than end index.
     */
    public static String getErrorMessageForInvalidRangeIndices() {
        return String.join(": ", MESSAGE_INVALID_PERSON_INDEX_RANGE,
                MESSAGE_START_INDEX_GREATER_THAN_END_INDEX);
    }

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating the duplicate indices.
     */
    public static String getErrorMessageForDuplicateIndices(Set<Index> duplicateIndices) {
        assert !duplicateIndices.isEmpty();

        Set<String> duplicateIndexStrings = duplicateIndices.stream()
                .map(index -> Integer.toString(index.getOneBased()))
                .collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_INDICES + String.join(", ", duplicateIndexStrings);
    }

    /**
     * Returns an error message indicating that there are no persons to perform the specified action on.
     */
    public static String getErrorMessageForNoPersons(String commandWord) {
        return String.format(MESSAGE_NO_PERSONS, commandWord);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; IC: ")
                .append(person.getIc())
                .append("; Urgency: ")
                .append(person.getUrgencyLevel())
                .append("; Next of Kin: ")
                .append(person.getNextOfKin())
                .append("; Next of Kin phone: ")
                .append(person.getNextOfKinPhone())
                .append("; Doctor: ")
                .append(person.getDoctorName())
                .append("; Symptoms: ");
        person.getSymptoms().forEach(builder::append);
        return builder.toString();
    }

}
