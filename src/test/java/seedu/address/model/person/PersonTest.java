package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_RS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SYMPTOM_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSymptoms().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same ic, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withSymptoms(VALID_SYMPTOM_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different ic, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withIc(VALID_IC_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // ic differs in case, all other attributes same -> returns True
        Person editedBob = new PersonBuilder(BOB).withIc(VALID_IC_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different nok phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withNextOfKinPhone(VALID_NEXT_OF_KIN_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different nok -> returns false
        editedAlice = new PersonBuilder(ALICE).withNextOfKin(VALID_NEXT_OF_KIN_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different nok relationship -> returns false
        editedAlice = new PersonBuilder(ALICE).withNextOfKinRelationship(VALID_NEXT_OF_KIN_RS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different symptoms -> returns false
        editedAlice = new PersonBuilder(ALICE).withSymptoms(VALID_SYMPTOM_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));

        // different notes -> returns false
        editedAlice = new PersonBuilder(ALICE).withNotes(VALID_NOTES_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail()
                + ", address=" + ALICE.getAddress()
                + ", symptoms=" + ALICE.getSymptoms()
                + ", ic=" + ALICE.getIc()
                + ", urgencyLevel=" + ALICE.getUrgencyLevel()
                + ", nextOfKinPhone=" + ALICE.getNextOfKinPhone()
                + ", doctorName=" + ALICE.getDoctorName()
                + ", nextOfKin=" + ALICE.getNextOfKin()
                + ", nextOfKinRelationship=" + ALICE.getNextOfKinRelationship()
                + ", notes=" + ALICE.getNotes()
                + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hashCode_includingNextOfKinPhone() {
        // same values -> equal hashCode
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // different nok phone -> different hashCode
        Person editedAlice = new PersonBuilder(ALICE).withNextOfKinPhone("81384393").build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());
    }

    @Test
    public void hashCode_includingDoctorName() {
        // same values -> equal hashCode
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // different doctorName -> different hashCode
        Person editedAlice = new PersonBuilder(ALICE).withDoctorName("Dr. John Doe").build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());
    }

    @Test
    public void hashCode_includingNameOfKin() {
        // same values -> equal hashCode
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // different NameOfKin -> different hashCode
        Person editedAlice = new PersonBuilder(ALICE).withNextOfKin("John Doe").build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());
    }

    @Test
    public void hashCode_includingNextOfKinRelationship() {
        // same values -> equal hashCode
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // different NameOfKin -> different hashCode
        Person editedAlice = new PersonBuilder(ALICE).withNextOfKinRelationship("Parent").build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());
    }

    @Test
    public void hashCode_includingNotes() {
        // same values -> equal hashCode
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // different doctorName -> different hashCode
        Person editedAlice = new PersonBuilder(ALICE).withNotes("An aspiring dentist").build();
        assertNotEquals(ALICE.hashCode(), editedAlice.hashCode());
    }
}
