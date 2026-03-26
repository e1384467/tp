package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class NextOfKinRelationshipTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NextOfKinRelationship(null));
    }

    @Test
    public void constructor_invalidCharacters_throwsIllegalArgumentException() {
        // contains numbers → invalid
        assertThrows(IllegalArgumentException.class, () -> new NextOfKinRelationship("Father123"));

        // contains special characters not allowed
        assertThrows(IllegalArgumentException.class, () -> new NextOfKinRelationship("Mother@Home"));
    }

    @Test
    public void constructor_validInput_success() {
        NextOfKinRelationship nok = new NextOfKinRelationship("Father");
        assertEquals("Father", nok.getLabel());
    }

    @Test
    public void isValidNextOfKinRelationship() {
        // valid inputs
        assertEquals(true, NextOfKinRelationship.isValidNextOfKinRelationship("Father"));
        assertEquals(true, NextOfKinRelationship.isValidNextOfKinRelationship("Mother-in-law"));
        assertEquals(true, NextOfKinRelationship.isValidNextOfKinRelationship("Aunt, Mary"));
        assertEquals(true, NextOfKinRelationship.isValidNextOfKinRelationship("Brother's Friend"));

        // invalid inputs
        assertEquals(false, NextOfKinRelationship.isValidNextOfKinRelationship("")); // empty
        assertEquals(false, NextOfKinRelationship.isValidNextOfKinRelationship("123"));
        assertEquals(false, NextOfKinRelationship.isValidNextOfKinRelationship("Dad@Home"));
    }

    @Test
    public void equals() {
        NextOfKinRelationship nok1 = new NextOfKinRelationship("Father");
        NextOfKinRelationship nok2 = new NextOfKinRelationship("Father");
        NextOfKinRelationship nok3 = new NextOfKinRelationship("father"); // case-insensitive
        NextOfKinRelationship nok4 = new NextOfKinRelationship("Mother");

        // same object
        assertEquals(nok1, nok1);

        // same values
        assertEquals(nok1, nok2);

        // case-insensitive equality
        assertEquals(nok1, nok3);

        // different values
        assertNotEquals(nok1, nok4);

        // different type
        assertNotEquals(nok1, 5);

        // null
        assertNotEquals(nok1, null);
    }

    @Test
    public void hashCode_consistency() {
        NextOfKinRelationship nok1 = new NextOfKinRelationship("Father");
        NextOfKinRelationship nok2 = new NextOfKinRelationship("father");

        // must be same since equals is case-insensitive
        assertEquals(nok1.hashCode(), nok2.hashCode());
    }

    @Test
    public void toString_returnsLabel() {
        NextOfKinRelationship nok = new NextOfKinRelationship("Sister");
        assertEquals("Sister", nok.toString());
    }
}
