package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NextOfKinPhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NextOfKinPhone(null));
    }

    @Test
    public void constructor_invalidNextOfKinPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new NextOfKinPhone(invalidPhone));
    }

    @Test
    public void isValidNextOfKinPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> NextOfKinPhone.isValidNextOfKinPhone(null));

        // invalid phone numbers
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("")); // empty string
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone(" ")); // spaces only
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("91")); // less than 3 numbers
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("phone")); // non-numeric
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("9011p041")); // alphabets within digits
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("9312 1534")); // spaces within digits
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("911")); // exactly 3 numbers
        assertFalse(NextOfKinPhone.isValidNextOfKinPhone("124293842033123")); // long phone numbers

        // valid phone numbers
        assertTrue(NextOfKinPhone.isValidNextOfKinPhone("93121534")); // exactly 8 numbers
    }

    @Test
    public void equals() {
        NextOfKinPhone nextOfKinPhone = new NextOfKinPhone("99999999");

        // same values -> returns true
        assertTrue(nextOfKinPhone.equals(new NextOfKinPhone("99999999")));

        // same object -> returns true
        assertTrue(nextOfKinPhone.equals(nextOfKinPhone));

        // null -> returns false
        assertFalse(nextOfKinPhone.equals(null));

        // different types -> returns false
        assertFalse(nextOfKinPhone.equals(5.0f));

        // different values -> returns false
        assertFalse(nextOfKinPhone.equals(new Phone("99555555")));
    }
}
