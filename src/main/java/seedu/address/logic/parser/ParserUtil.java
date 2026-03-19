package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;
import seedu.address.model.symptom.Symptom;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String symptom} into a {@code Symptom}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code symptom} is invalid.
     */
    public static Symptom parseSymptom(String symptom) throws ParseException {
        requireNonNull(symptom);
        String trimmedSymptom = symptom.trim();
        if (!Symptom.isValidSymptomName(trimmedSymptom)) {
            throw new ParseException(Symptom.MESSAGE_CONSTRAINTS);
        }
        return new Symptom(trimmedSymptom);
    }

    /**
     * Parses {@code Collection<String> symptoms} into a {@code Set<Symptom>}.
     */
    public static Set<Symptom> parseSymptoms(Collection<String> symptoms) throws ParseException {
        requireNonNull(symptoms);
        final Set<Symptom> symptomSet = new HashSet<>();
        for (String symptomName : symptoms) {
            symptomSet.add(parseSymptom(symptomName));
        }
        return symptomSet;
    }

    /**
     * Parses a {@code String urgencyLevel} into an {@code UrgencyLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code urgencyLevel} is invalid.
     */
    public static UrgencyLevel parseUrgencyLevel(String urgencyLevel) throws ParseException {
        requireNonNull(urgencyLevel);
        String trimmedUrgencyLevel = urgencyLevel.trim();
        if (!UrgencyLevel.isValidUrgencyLevel(trimmedUrgencyLevel)) {
            throw new ParseException(UrgencyLevel.MESSAGE_CONSTRAINTS);
        }
        return new UrgencyLevel(trimmedUrgencyLevel);
    }

    /**
     * Parses {@code String ic} into an {@code Ic}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ic} is invalid.
     */
    public static Ic parseIc(String ic) throws ParseException {
        requireNonNull(ic);
        String trimmedIc = ic.trim();
        if (!Ic.isValidIc(trimmedIc)) {
            throw new ParseException(Ic.MESSAGE_CONSTRAINTS);
        }
        return new Ic(trimmedIc);
    }
}
