package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Doctor in the system.
 * Guarantees: immutable; name is validated and non-null.
 */
public class DoctorName {

    public static final String MESSAGE_CONSTRAINTS =
            "For the names, please use only these characters: (A-Z, a-z), spaces, comma (,), "
                    + "hyphens (-), apostrophe (‘), period (.) for the doctor name.";

    public static final String MESSAGE_EMPTY =
            "The doctor name field cannot be empty.";

    // Regex: letters + allowed punctuation + spaces
    // ^ start, $ end
    public static final String VALIDATION_REGEX = "[A-Za-z ,.'-]+";

    private final String fullName;

    /**
     * Constructs a {@code DoctorName}.
     *
     * @param name A valid doctor name.
     */
    public DoctorName(String name) {
        Objects.requireNonNull(name);

        String trimmed = name.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_EMPTY);
        }

        if (!isValidName(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        this.fullName = trimmed;
    }

    /**
     * Returns true if a given string is a valid doctor name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DoctorName)) {
            return false;
        }
        DoctorName otherDoctor = (DoctorName) other;
        return fullName.equalsIgnoreCase(otherDoctor.fullName); // case-insensitive
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode(); // consistent with equals
    }
}
