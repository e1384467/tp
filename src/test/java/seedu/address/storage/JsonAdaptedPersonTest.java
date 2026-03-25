package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.NextOfKinPhone;
import seedu.address.model.person.NextOfKinRelationship;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_SYMPTOM = "#friend";
    private static final String INVALID_IC = "S1234567";
    private static final String INVALID_URGENCY_LEVEL = "urgent";
    private static final String INVALID_NEXT_OF_KIN_PHONE = "9213a";
    private static final String INVALID_NEXT_OF_KIN_RELATIONSHIP = "M@ther";
    private static final String INVALID_DOCTOR_NAME = "d@ctor";
    private static final String INVALID_NEXT_OF_KIN = "J@nny";
    private static final String INVALID_NOTES = "a".repeat(Notes.MAX_LENGTH + 10);

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedSymptom> VALID_SYMPTOMS = BENSON.getSymptoms().stream()
            .map(JsonAdaptedSymptom::new)
            .collect(Collectors.toList());
    private static final String VALID_IC = BENSON.getIc().toString();
    private static final String VALID_URGENCY_LEVEL = BENSON.getUrgencyLevel().toString();
    private static final String VALID_NEXT_OF_KIN_PHONE = BENSON.getNextOfKinPhone().toString();
    private static final String VALID_DOCTOR_NAME = BENSON.getDoctorName().toString();
    private static final String VALID_NEXT_OF_KIN = BENSON.getNextOfKin().toString();
    private static final String VALID_NOTES = BENSON.getNotes().toString();
    private static final String VALID_NEXT_OF_KIN_RELATIONSHIP = BENSON.getNextOfKinRelationship().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_SYMPTOMS,
                VALID_IC,
                VALID_URGENCY_LEVEL,
                VALID_DOCTOR_NAME,
                VALID_NEXT_OF_KIN_PHONE,
                VALID_NEXT_OF_KIN,
                VALID_NEXT_OF_KIN_RELATIONSHIP,
                VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        INVALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                null,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_SYMPTOMS,
                VALID_IC,
                VALID_URGENCY_LEVEL,
                VALID_DOCTOR_NAME,
                VALID_NEXT_OF_KIN_PHONE,
                VALID_NEXT_OF_KIN,
                VALID_NEXT_OF_KIN_RELATIONSHIP,
                VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        INVALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                null,
                VALID_ADDRESS,
                VALID_SYMPTOMS,
                VALID_IC,
                VALID_URGENCY_LEVEL,
                VALID_DOCTOR_NAME,
                VALID_NEXT_OF_KIN_PHONE,
                VALID_NEXT_OF_KIN,
                VALID_NEXT_OF_KIN_RELATIONSHIP,
                VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        INVALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                null,
                VALID_SYMPTOMS,
                VALID_IC,
                VALID_URGENCY_LEVEL,
                VALID_DOCTOR_NAME,
                VALID_NEXT_OF_KIN_PHONE,
                VALID_NEXT_OF_KIN,
                VALID_NEXT_OF_KIN_RELATIONSHIP,
                VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSymptoms_throwsIllegalValueException() {
        List<JsonAdaptedSymptom> invalidSymptoms = new ArrayList<>(VALID_SYMPTOMS);
        invalidSymptoms.add(new JsonAdaptedSymptom(INVALID_SYMPTOM));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        invalidSymptoms,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_nullIc_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_SYMPTOMS,
                null,
                VALID_URGENCY_LEVEL,
                VALID_DOCTOR_NAME,
                VALID_NEXT_OF_KIN_PHONE,
                VALID_NEXT_OF_KIN,
                VALID_NEXT_OF_KIN_RELATIONSHIP,
                VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ic.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidIc_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        INVALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = Ic.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidUrgencyLevel_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        INVALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = UrgencyLevel.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUrgencyLevel_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        null,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, UrgencyLevel.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_nullNextOfKinPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_SYMPTOMS,
                VALID_IC,
                VALID_URGENCY_LEVEL,
                VALID_DOCTOR_NAME,
                null,
                VALID_NEXT_OF_KIN,
                VALID_NEXT_OF_KIN_RELATIONSHIP,
                VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NextOfKinPhone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNextOfKinPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        INVALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = NextOfKinPhone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDoctorName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        INVALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = DoctorName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDoctorName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        null,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DoctorName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNextOfKin_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        INVALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = NextOfKin.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNextOfKin_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        null,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, NextOfKin.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNotes_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        VALID_NEXT_OF_KIN_RELATIONSHIP,
                        INVALID_NOTES
                );
        String expectedMessage = Notes.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNextOfKinRelationship_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        INVALID_NEXT_OF_KIN_RELATIONSHIP,
                        VALID_NOTES
                );
        String expectedMessage = NextOfKinRelationship.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNextOfKinRelationship_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(
                        VALID_NAME,
                        VALID_PHONE,
                        VALID_EMAIL,
                        VALID_ADDRESS,
                        VALID_SYMPTOMS,
                        VALID_IC,
                        VALID_URGENCY_LEVEL,
                        VALID_DOCTOR_NAME,
                        VALID_NEXT_OF_KIN_PHONE,
                        VALID_NEXT_OF_KIN,
                        null,
                        VALID_NOTES
                );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                NextOfKinRelationship.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
