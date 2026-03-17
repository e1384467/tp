package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's urgency level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrgencyLevel(String)}
 */
public class UrgencyLevel {

    /**
     * Defines the urgency levels that a person can have. The levels are ordered from least urgent to most urgent.
     */
    public enum Level {
        LOW,
        MODERATE,
        HIGH,
        EXTREME;
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Urgency levels should be one of the following: low, moderate, high, extreme.";
    public static final String VALIDATION_REGEX = "(?i)^(" + Level.LOW + "|"
            + Level.MODERATE + "|"
            + Level.HIGH + "|"
            + Level.EXTREME + ")$";

    public final Level level;

    /**
     * Constructs an {@code UrgencyLevel}.
     *
     * @param level A valid urgency level.
     */
    public UrgencyLevel(String level) {
        requireNonNull(level);
        checkArgument(isValidUrgencyLevel(level), MESSAGE_CONSTRAINTS);
        this.level = Level.valueOf(level.toUpperCase());
    }

    public static boolean isValidUrgencyLevel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return level.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UrgencyLevel)) {
            return false;
        }

        UrgencyLevel otherUrgencyLevel = (UrgencyLevel) other;
        return level.equals(otherUrgencyLevel.level);
    }
}
