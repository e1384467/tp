package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * Represents the notes field for a patient.
 * Guarantees: immutable, valid as per constraints.
 */
public class Notes {

    public static final int MAX_LENGTH = 500;
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid value:  The notes field exceeds the maximum limit of " + MAX_LENGTH + " characters.";

    private final String value;

    /**
     * Constructs a {@code Notes}.
     * @param notes A string representing the notes. Can be null or empty.
     * @throws IllegalArgumentException if the notes exceed the maximum length
     */
    public Notes(String notes) {
        requireNonNull(notes);
        checkArgument(isValidNotes(notes), MESSAGE_CONSTRAINTS);
        this.value = notes;
    }

    /**
     * Appends additional notes to the existing notes, automatically adding a timestamp.
     * Safely ignores empty strings (No-Op) and overwrites default placeholders.
     * @param additionalNotes The new notes to append.
     * @return A new Notes object containing the combined text.
     */
    public Notes append(Notes additionalNotes) {
        // 1. NO-OP GUARD: Safely ignore empty strings
        if (additionalNotes.value.trim().isEmpty()) {
            return this;
        }

        // 2. Generate the timestamp internally
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM HH:mm"));
        String formattedAppend = "[" + timestamp + "] " + additionalNotes.value;

        String existingText = this.value;

        // 3. Handle overwriting empty strings (Dash check removed!)
        if (existingText.trim().isEmpty()) {
            return new Notes(formattedAppend);
        }

        // 4. Combine existing notes with the new timestamped note
        // (This will automatically throw an IllegalArgumentException from the constructor if it exceeds MAX_LENGTH)
        return new Notes(existingText + "\n" + formattedAppend);
    }

    /**
     * Returns true if a given string is a valid notes value.
     */
    public static boolean isValidNotes(String test) {
        return test.length() <= MAX_LENGTH;
    }

    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                && value.equals(((Notes) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
