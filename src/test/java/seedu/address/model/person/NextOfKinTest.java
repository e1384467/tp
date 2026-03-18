package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NextOfKinTest {

    // Constructor tests
    @Test
    public void constructor_validName_success() {
        String validName = "John O'Conner, M.D.";
        NextOfKin nextOfKin = new NextOfKin(validName);
        assertEquals("John O'Conner, M.D.", nextOfKin.getFullName());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NextOfKin(null));
    }

    @Test
    public void constructor_emptyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new NextOfKin("   "));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "@John#Doe"; // contains invalid characters
        assertThrows(IllegalArgumentException.class, () -> new NextOfKin(invalidName));
    }

    @Test
    public void constructor_trimsLeadingAndTrailingSpaces() {
        NextOfKin nextOfKin = new NextOfKin("   Jane Doe  ");
        assertEquals("Jane Doe", nextOfKin.getFullName());
    }

    // isValidNextOfKin tests
    @Test
    public void isValidNextOfKin_validNames_returnsTrue() {
        assertTrue(NextOfKin.isValidNextOfKin("John Doe"));
        assertTrue(NextOfKin.isValidNextOfKin("O'Conner, Jane"));
        assertTrue(NextOfKin.isValidNextOfKin("Anne-Marie Smith"));
        assertTrue(NextOfKin.isValidNextOfKin("John A. Doe"));
        assertTrue(NextOfKin.isValidNextOfKin("John, M.D."));
    }

    @Test
    public void isValidNextOfKin_invalidNames_returnsFalse() {
        assertFalse(NextOfKin.isValidNextOfKin("")); // empty
        assertFalse(NextOfKin.isValidNextOfKin("Mr@John")); // invalid characters
        assertFalse(NextOfKin.isValidNextOfKin("John123")); // numbers not allowed
        assertFalse(NextOfKin.isValidNextOfKin("Ms#Jane!")); // special characters #!
    }

    // Equals and hashCode tests
    @Test
    public void equals_sameObject_returnsTrue() {
        NextOfKin nextOfKin = new NextOfKin("John Doe");
        assertEquals(nextOfKin, nextOfKin);
    }

    @Test
    public void equals_sameNameDifferentCase_returnsTrue() {
        NextOfKin name1 = new NextOfKin("John Doe");
        NextOfKin name2 = new NextOfKin("john doe");
        assertEquals(name1, name2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        NextOfKin name1 = new NextOfKin("John Doe");
        NextOfKin name2 = new NextOfKin("Jane Doe");
        assertNotEquals(name1, name2);
    }

    @Test
    public void equals_notNextOfKin_returnsFalse() {
        NextOfKin nextOfKin = new NextOfKin("John Doe");
        assertNotEquals(nextOfKin, "John Doe"); // different type
    }

    @Test
    public void hashCode_sameNameDifferentCase_equalHashCode() {
        NextOfKin name1 = new NextOfKin("John Doe");
        NextOfKin name2 = new NextOfKin("john doe");
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    // toString test
    @Test
    public void toString_returnsFullName() {
        NextOfKin nextOfKin = new NextOfKin("John Doe");
        assertEquals("John Doe", nextOfKin.toString());
    }
}
