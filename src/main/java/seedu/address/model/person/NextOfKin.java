package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Next-of-kin in the system.
 * Guarantees: immutable; name is validated and non-null.
 */
public class NextOfKin {

    public static final String MESSAGE_CONSTRAINTS =
            "For next-of-kin, please use only these characters: (A-Z, a-z), spaces, comma (,), "
                    + "hyphens (-), apostrophe (‘), period (.) for the name of next-of-kin.";

    public static final String MESSAGE_EMPTY =
            "The next-of-kin field cannot be empty.";

    // Regex: letters + allowed punctuation + spaces
    // ^ start, $ end
    public static final String VALIDATION_REGEX = "[A-Za-z ,.'-]+";

    private final String fullName;

    /**
     * Constructs a {@code NextOfKin}.
     *
     * @param name A valid doctor name.
     */
    public NextOfKin(String name) {
        Objects.requireNonNull(name);

        String trimmed = name.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_EMPTY);
        }

        if (!isValidNextOfKin(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        this.fullName = trimmed;
    }

    /**
     * Returns true if a given string is a valid next-of-kin.
     */
    public static boolean isValidNextOfKin(String test) {
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
        if (!(other instanceof NextOfKin)) {
            return false;
        }
        NextOfKin otherNok = (NextOfKin) other;
        return fullName.equalsIgnoreCase(otherNok.fullName); // case-insensitive
    }

    @Override
    public int hashCode() {
        return fullName.toLowerCase().hashCode(); // consistent with equals
    }
}
