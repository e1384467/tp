package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOCTOR_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_RS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NEXT_OF_KIN_RS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SYMPTOM_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SYMPTOM_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URGENCY_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URGENCY_LEVEL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withSymptoms("friends")
            .withIc("S1111111A")
            .withUrgencyLevel("low")
            .withNextOfKinPhone("91234567")
            .withDoctorName("Ben Leong")
            .withNextOfKin("Benny Blanco")
            .withNextOfKinRelationship("Father")
            .withNotes("Keeps saying hi")
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withSymptoms("owesMoney", "friends")
            .withIc("S2222222A")
            .withUrgencyLevel("high")
            .withNextOfKinPhone("92345678")
            .withDoctorName("Colin")
            .withNextOfKin("Selena Gomez")
            .withNextOfKinRelationship("Mother")
            .withNotes("Cannot sit down")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withIc("S3333333A")
            .withUrgencyLevel("low")
            .withNextOfKinPhone("93456789")
            .withDoctorName("Tzer Bin")
            .withNextOfKin("Justin Bieber")
            .withNextOfKinRelationship("Brother")
            .withNotes("Funny guy")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withSymptoms("friends")
            .withIc("S4444444A")
            .withUrgencyLevel("extreme")
            .withNextOfKinPhone("94567890")
            .withDoctorName("Lady Gaga")
            .withNextOfKin("Hailey Bieber")
            .withNextOfKinRelationship("Sibling")
            .withNotes("Arrested for harrassment")
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("94822244")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withIc("S5555555A")
            .withUrgencyLevel("high")
            .withNextOfKinPhone("95678901")
            .withDoctorName("Spiderman")
            .withNextOfKin("Kendall Jenner")
            .withNextOfKinRelationship("Sister")
            .withNotes("Cannot stand up")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("94824275")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withIc("S6666666A")
            .withUrgencyLevel("moderate")
            .withNextOfKinPhone("96789012")
            .withDoctorName("Ironman")
            .withNextOfKin("Kylie Jenner")
            .withNextOfKinRelationship("Family member")
            .withNotes("Hates walking")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("94824426")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withIc("S7777777A")
            .withUrgencyLevel("extreme")
            .withNextOfKinPhone("97890123")
            .withDoctorName("Choong")
            .withNextOfKin("Kris Jenner")
            .withNextOfKinRelationship("Guardian")
            .withNotes("Handsome man")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("84824247")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withIc("S8888888A")
            .withUrgencyLevel("low")
            .withNextOfKinPhone("98901234")
            .withDoctorName("Boong")
            .withNextOfKin("Benson Boone")
            .withNextOfKinRelationship("Sibling")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("84821318")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withIc("S9999999A")
            .withUrgencyLevel("moderate")
            .withNextOfKinPhone("99012345")
            .withDoctorName("Captain America")
            .withNextOfKin("Cayydences")
            .withNextOfKinRelationship("Guardian")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withSymptoms(VALID_SYMPTOM_FRIEND)
            .withIc(VALID_IC_AMY)
            .withUrgencyLevel(VALID_URGENCY_LEVEL_AMY)
            .withNextOfKinPhone(VALID_NEXT_OF_KIN_PHONE_AMY)
            .withDoctorName(VALID_DOCTOR_NAME_AMY)
            .withNextOfKin(VALID_NEXT_OF_KIN_AMY)
            .withNextOfKinRelationship(VALID_NEXT_OF_KIN_RS_AMY)
            .withNotes(VALID_NOTES_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withSymptoms(VALID_SYMPTOM_HUSBAND, VALID_SYMPTOM_FRIEND)
            .withIc(VALID_IC_BOB)
            .withUrgencyLevel(VALID_URGENCY_LEVEL_BOB)
            .withNextOfKinPhone(VALID_NEXT_OF_KIN_PHONE_BOB)
            .withDoctorName(VALID_DOCTOR_NAME_BOB)
            .withNextOfKin(VALID_NEXT_OF_KIN_BOB)
            .withNextOfKinRelationship(VALID_NEXT_OF_KIN_RS_BOB)
            .withNotes(VALID_NOTES_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
