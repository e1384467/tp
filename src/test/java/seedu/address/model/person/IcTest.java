package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IcTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Ic(null));
    }

    @Test
    public void constructor_invalidIc_throwsIllegalArgumentException() {
        String invalidIc = "";
        assertThrows(IllegalArgumentException.class, () -> new Ic(invalidIc));
    }

    @Test
    public void isValidIc() {

        // null Ic
        assertThrows(NullPointerException.class, () -> Ic.isValidIc(null));

        // blank Ic
        assertFalse(Ic.isValidIc("")); // empty string
        assertFalse(Ic.isValidIc(" ")); // spaces only

        // invalid Ic
        assertFalse(Ic.isValidIc("123456A")); // less than 7 digits, no first character
        assertFalse(Ic.isValidIc("S123456A")); // less than 7 digits, with first character
        assertFalse(Ic.isValidIc("A123456A")); // less than 7 digits, with invalid first character
        assertFalse(Ic.isValidIc("12345678A")); // more than 7 digits, no first character
        assertFalse(Ic.isValidIc("S12345678A")); // more than 7 digits, with first character
        assertFalse(Ic.isValidIc("A12345678A")); // more than 7 digits, with invalid first character
        assertFalse(Ic.isValidIc("S123456")); // less than 7 digits, no last character
        assertFalse(Ic.isValidIc("S123456A")); // less than 7 digits, with last character
        assertFalse(Ic.isValidIc("S123456_")); // less than 7 digits, with underscore last character
        assertFalse(Ic.isValidIc("S12345678")); // more than 7 digits, no last character
        assertFalse(Ic.isValidIc("S12345678A")); // more than 7 digits, with last character
        assertFalse(Ic.isValidIc("S12345678_")); // more than 7 digits, with underscore last character
        assertFalse(Ic.isValidIc("1234567A")); // first character is missing
        assertFalse(Ic.isValidIc("X1234567A")); // first character is not S, T, F or G
        assertFalse(Ic.isValidIc("_1234567A")); // first character is underscore, not S, T, F or G
        assertFalse(Ic.isValidIc("+1234567A")); // first character is '+' symbol, not S, T, F or G
        assertFalse(Ic.isValidIc(" 1234567A")); // first character is space, not S, T, F or G
        assertFalse(Ic.isValidIc(".1234567A")); // first character is period, not S, T, F or G
        assertFalse(Ic.isValidIc("-1234567A")); // first character is '-' symbol, not S, T, F or G
        assertFalse(Ic.isValidIc("@1234567A")); // first character is '@' symbol, not S, T, F or G
        assertFalse(Ic.isValidIc(",1234567A")); // first character is ',' symbol, not S, T, F or G
        assertFalse(Ic.isValidIc("+x1234567A")); // first character has 2 characters, both invalid
        assertFalse(Ic.isValidIc("+S1234567A")); // first character has 2 characters, one invalid then valid
        assertFalse(Ic.isValidIc("S+1234567A")); // first character has 2 characters, one valid then invalid
        assertFalse(Ic.isValidIc("FS1234567A")); // first character has 2 characters, both valid
        assertFalse(Ic.isValidIc("S1234567")); // missing last character
        assertFalse(Ic.isValidIc("S1234567_")); // last character is underscore, not a letter
        assertFalse(Ic.isValidIc("S1234567+")); // last character is '+' symbol, not a letter
        assertFalse(Ic.isValidIc("S1234567 ")); // last character is space, not a letter
        assertFalse(Ic.isValidIc("S1234567.")); // last character is period, not a letter
        assertFalse(Ic.isValidIc("S1234567-")); // last character is '-' symbol, not a letter
        assertFalse(Ic.isValidIc("S1234567@")); // last character is '@' symbol, not a letter
        assertFalse(Ic.isValidIc("S1234567,")); // last character is ',' symbol, not a letter
        assertFalse(Ic.isValidIc("S1234567@@")); // last character has 2 characters, both invalid
        assertFalse(Ic.isValidIc("S1234567@A")); // last character has 2 characters, one invalid then valid
        assertFalse(Ic.isValidIc("S1234567A@")); // last character has 2 characters, one valid then invalid
        assertFalse(Ic.isValidIc("S1234567AB")); // last character has 2 characters, both valid
        assertFalse(Ic.isValidIc("S1234A56A")); // letter in the middle of 6 digits
        assertFalse(Ic.isValidIc("SS123456A")); // letter in the start of 6 digits
        assertFalse(Ic.isValidIc("S1234A56AA")); // letter in the end of 6 digits

        // valid Ic
        assertTrue(Ic.isValidIc("S1234567A"));
        assertTrue(Ic.isValidIc("s9999999A")); // case-insensitive
        assertTrue(Ic.isValidIc("S9999999a")); // case-insensitive
        assertTrue(Ic.isValidIc("s9999999a")); // case-insensitive
        assertTrue(Ic.isValidIc("T7654321Z"));
        assertTrue(Ic.isValidIc("t7654321Z")); // case-insensitive
        assertTrue(Ic.isValidIc("T7654321z")); // case-insensitive
        assertTrue(Ic.isValidIc("t7654321t")); // case-insensitive
        assertTrue(Ic.isValidIc("F0000000B"));
        assertTrue(Ic.isValidIc("f0000000B")); // case-insensitive
        assertTrue(Ic.isValidIc("F0000000b")); // case-insensitive
        assertTrue(Ic.isValidIc("f0000000b")); // case-insensitive
        assertTrue(Ic.isValidIc("G9999999Y"));
        assertTrue(Ic.isValidIc("g9999999Y")); // case-insensitive
        assertTrue(Ic.isValidIc("G9999999y")); // case-insensitive
        assertTrue(Ic.isValidIc("g9999999y")); // case-insensitive

    }

    @Test
    public void equals() {
        Ic ic = new Ic("G9999999Y");

        // same values -> returns true
        assertTrue(ic.equals(new Ic("G9999999Y"))); // exact same
        assertTrue(ic.equals(new Ic("g9999999Y"))); // case-insensitive
        assertTrue(ic.equals(new Ic("G9999999y"))); // case-insensitive
        assertTrue(ic.equals(new Ic("g9999999Y"))); // case-insensitive

        // same object -> returns true
        assertTrue(ic.equals(ic));

        // null -> returns false
        assertFalse(ic.equals(null));

        // different types -> returns false
        assertFalse(ic.equals(5.0f));

        // different values -> returns false
        assertFalse(ic.equals(new Ic("F0000000B"))); // completely different
        assertFalse(ic.equals(new Ic("G9999999Z"))); // same digits but different last character
        assertFalse(ic.equals(new Ic("S9999999Y"))); // same digits but different first character
        assertFalse(ic.equals(new Ic("S9999998Y"))); // different digits but same first and last character

    }
}

