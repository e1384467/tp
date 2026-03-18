package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's urgency level in the clinical address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrgencyLevel(String)}
 */
public class UrgencyLevel implements Comparable<UrgencyLevel> {

    /**
     * Defines the levels of medical urgency with associated priority values for sorting.
     */
    public enum Level {
        LOW(1),
        MODERATE(2),
        HIGH(3),
        EXTREME(4);

        private final int priority;

        Level(int priority) {
            this.priority = priority;
        }

        /**
         * Returns the numerical priority of the urgency level.
         *
         * @return An integer representing the sorting priority.
         */
        public int getPriority() {
            return priority;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Urgency levels should be one of the following: low, moderate, high, extreme.";

    /**
     * Regex for validating urgency levels. Case-insensitive matching for low, moderate, high, and extreme.
     */
    public static final String VALIDATION_REGEX = "(?i)^(low|moderate|high|extreme)$";

    public final Level level;

    /**
     * Constructs an {@code UrgencyLevel}.
     *
     * @param level A valid urgency level string.
     */
    public UrgencyLevel(String level) {
        requireNonNull(level);
        checkArgument(isValidUrgencyLevel(level), MESSAGE_CONSTRAINTS);
        this.level = Level.valueOf(level.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid urgency level.
     *
     * @param test The string to validate.
     * @return True if the string matches one of the defined urgency levels.
     */
    public static boolean isValidUrgencyLevel(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the CSS style class name associated with this urgency level.
     * This is used by JavaFX to apply color-coding to the UI components.
     *
     * @return A string in the format "urgency-[level]" (e.g., "urgency-high").
     */
    public String getStyleClass() {
        return "urgency-" + level.toString().toLowerCase();
    }

    @Override
    public String toString() {
        return level.toString();
    }

    /**
     * Compares this UrgencyLevel with another based on their priority values.
     * Higher priority values (e.g. EXTREME) come before lower ones in the list.
     */
    @Override
    public int compareTo(UrgencyLevel other) {
        return Integer.compare(other.level.getPriority(), this.level.getPriority());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UrgencyLevel)) {
            return false;
        }

        UrgencyLevel otherUrgencyLevel = (UrgencyLevel) other;
        return level.equals(otherUrgencyLevel.level);
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
