package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Next-of-kin relationship in the system.
 * Guarantees: immutable; name is validated and non-null.
 */
public class NextOfKinRelationship {

    public static final String MESSAGE_CONSTRAINTS =
            "Please use only these characters: (A-Z, a-z), spaces, comma (,), hyphens (-), apostrophe ('), "
                    + "period (.) for the next-of-kin name";

    public static final String MESSAGE_EMPTY =
            "The next-of-kin relationship field cannot be empty.";

    // Regex: letters + allowed punctuation + spaces
    // ^ start, $ end
    public static final String VALIDATION_REGEX = "[A-Za-z ,.'-]+";

    private final String label;

    /**
     * Constructs a {@code NextOfKinRelationship}.
     *
     * @param label A valid next of kin relationship label.
     */
    public NextOfKinRelationship(String label) {
        Objects.requireNonNull(label);

        String trimmed = label.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_EMPTY);
        }

        if (!isValidNextOfKinRelationship(trimmed)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        this.label = trimmed;
    }

    /**
     * Returns true if a given string is a valid next-of-kin.
     */
    public static boolean isValidNextOfKinRelationship(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NextOfKinRelationship)) {
            return false;
        }
        NextOfKinRelationship otherNok = (NextOfKinRelationship) other;
        return label.equalsIgnoreCase(otherNok.label); // case-insensitive
    }

    @Override
    public int hashCode() {
        return label.toLowerCase().hashCode(); // consistent with equals
    }
}
