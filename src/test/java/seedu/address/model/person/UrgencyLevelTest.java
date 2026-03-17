package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UrgencyLevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UrgencyLevel(null));
    }

    @Test
    public void constructor_invalidUrgencyLevel_throwsIllegalArgumentException() {
        String invalidUrgencyLevel = "";
        assertThrows(IllegalArgumentException.class, () -> new UrgencyLevel(invalidUrgencyLevel));
    }

    @Test
    public void isValidUrgencyLevel() {
        // null urgency level
        assertThrows(NullPointerException.class, () -> UrgencyLevel.isValidUrgencyLevel(null));

        // invalid urgency levels
        assertFalse(UrgencyLevel.isValidUrgencyLevel("")); // empty string
        assertFalse(UrgencyLevel.isValidUrgencyLevel(" ")); // spaces only
        assertFalse(UrgencyLevel.isValidUrgencyLevel("urgent")); // not a valid level

        // valid urgency levels
        assertTrue(UrgencyLevel.isValidUrgencyLevel("low"));
        assertTrue(UrgencyLevel.isValidUrgencyLevel("lOw")); // case-insensitive
        assertTrue(UrgencyLevel.isValidUrgencyLevel("moderate"));
        assertTrue(UrgencyLevel.isValidUrgencyLevel("modErAte")); // case-insensitive
        assertTrue(UrgencyLevel.isValidUrgencyLevel("high"));
        assertTrue(UrgencyLevel.isValidUrgencyLevel("HiGh")); // case-insensitive
        assertTrue(UrgencyLevel.isValidUrgencyLevel("extreme"));
        assertTrue(UrgencyLevel.isValidUrgencyLevel("extREme")); // case-insensitive

    }

    @Test
    public void equals() {
        UrgencyLevel lowUrgency = new UrgencyLevel("low");
        UrgencyLevel moderateUrgency = new UrgencyLevel("moderate");

        // same values -> returns true
        assertTrue(lowUrgency.equals(new UrgencyLevel("low")));
        assertTrue(moderateUrgency.equals(new UrgencyLevel("moderate")));

        // same object -> returns true
        assertTrue(lowUrgency.equals(lowUrgency));
        assertTrue(moderateUrgency.equals(moderateUrgency));

        // null -> returns false
        assertFalse(lowUrgency.equals(null));
        assertFalse(moderateUrgency.equals(null));

        // different types -> returns false
        assertFalse(lowUrgency.equals(5.0f));
        assertFalse(moderateUrgency.equals(5.0f));

        // different values -> returns false
        assertFalse(lowUrgency.equals(moderateUrgency));

    }
}
