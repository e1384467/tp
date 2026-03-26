package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DoctorNameTest {

    // Constructor tests
    @Test
    public void constructor_validName_success() {
        String validName = "Dr. John O'Conner, M.D.";
        DoctorName doctorName = new DoctorName(validName);
        assertEquals("Dr. John O'Conner, M.D.", doctorName.getFullName());
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DoctorName(null));
    }

    @Test
    public void constructor_emptyName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DoctorName("   "));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "Dr@John#Doe"; // contains invalid characters
        assertThrows(IllegalArgumentException.class, () -> new DoctorName(invalidName));
    }

    @Test
    public void constructor_withLeadingAndTrailingWhitespaces_throwsIllegalArgumentException() {
        String invalidName = "   Dr. Jane Doe  ";
        assertThrows(IllegalArgumentException.class, () -> new DoctorName(invalidName));
    }

    // isValidName tests
    @Test
    public void isValidName_validNames_returnsTrue() {
        assertTrue(DoctorName.isValidName("John Doe"));
        assertTrue(DoctorName.isValidName("O'Conner, Jane"));
        assertTrue(DoctorName.isValidName("Anne-Marie Smith"));
        assertTrue(DoctorName.isValidName("Dr. John A. Doe"));
        assertTrue(DoctorName.isValidName("Dr John, M.D."));
    }

    @Test
    public void isValidName_invalidNames_returnsFalse() {
        assertFalse(DoctorName.isValidName("")); // empty
        assertFalse(DoctorName.isValidName("Dr@John")); // invalid characters
        assertFalse(DoctorName.isValidName("John123")); // numbers not allowed
        assertFalse(DoctorName.isValidName("Dr#Jane!")); // special characters #!
    }

    // Equals and hashCode tests
    @Test
    public void equals_sameObject_returnsTrue() {
        DoctorName doctorName = new DoctorName("John Doe");
        assertEquals(doctorName, doctorName);
    }

    @Test
    public void equals_sameNameDifferentCase_returnsTrue() {
        DoctorName name1 = new DoctorName("John Doe");
        DoctorName name2 = new DoctorName("john doe");
        assertEquals(name1, name2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        DoctorName name1 = new DoctorName("John Doe");
        DoctorName name2 = new DoctorName("Jane Doe");
        assertNotEquals(name1, name2);
    }

    @Test
    public void equals_notDoctorName_returnsFalse() {
        DoctorName doctorName = new DoctorName("John Doe");
        assertNotEquals(doctorName, "John Doe"); // different type
    }

    @Test
    public void hashCode_sameNameDifferentCase_equalHashCode() {
        DoctorName name1 = new DoctorName("John Doe");
        DoctorName name2 = new DoctorName("john doe");
        assertEquals(name1.hashCode(), name2.hashCode());
    }

    // toString test
    @Test
    public void toString_returnsFullName() {
        DoctorName doctorName = new DoctorName("John Doe");
        assertEquals("John Doe", doctorName.toString());
    }
}
