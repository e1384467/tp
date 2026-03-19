package seedu.address.model.symptom;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Symptom in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSymptomName(String)}
 */
public class Symptom {

    public static final String MESSAGE_CONSTRAINTS = "Symptom names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String symptomName;

    /**
     * Constructs a {@code Symptom}.
     *
     * @param symptomName A valid symptom name.
     */
    public Symptom(String symptomName) {
        requireNonNull(symptomName);
        checkArgument(isValidSymptomName(symptomName), MESSAGE_CONSTRAINTS);
        this.symptomName = symptomName;
    }

    /**
     * Returns true if a given string is a valid symptom name.
     */
    public static boolean isValidSymptomName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Symptom)) {
            return false;
        }

        Symptom otherSymptom = (Symptom) other;
        return symptomName.equals(otherSymptom.symptomName);
    }

    @Override
    public int hashCode() {
        return symptomName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + symptomName + ']';
    }

}
