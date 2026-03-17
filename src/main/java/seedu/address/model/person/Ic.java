package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's IC in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIc(String)}
 */
public class Ic {

    public static final String MESSAGE_CONSTRAINTS =
            "IC should only contain 1 character at the start, followed by 7 digits, and end with 1 character. "
                    + "It should not be blank.\n"
                    + "The first character should be S, T, F or G.\n"
                    + "The 7 digits in the middle should be between 0000000 and 9999999.\n"
                    + "The last character can be any letter from A to Z.";

    public static final String VALIDATION_REGEX = "(?i)^[STFGstfg]\\d{7}[A-Za-z]$";

    public final String value;

    /**
     * Constructs an {@code Ic}.
     *
     * @param ic A valid IC.
     */
    public Ic(String ic) {
        requireNonNull(ic);
        checkArgument(isValidIc(ic), MESSAGE_CONSTRAINTS);
        this.value = ic;
    }

    public static boolean isValidIc(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Ic)) {
            return false;
        }

        Ic otherIc = (Ic) other;
        return value.equalsIgnoreCase(otherIc.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
