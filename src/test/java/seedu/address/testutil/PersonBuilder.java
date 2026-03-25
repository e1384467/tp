package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.NextOfKinPhone;
import seedu.address.model.person.NextOfKinRelationship;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;
import seedu.address.model.symptom.Symptom;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_IC = "S1234567A";
    public static final String DEFAULT_URGENCY_LEVEL = "low";
    public static final String DEFAULT_NEXT_OF_KIN_PHONE = "81234567";
    public static final String DEFAULT_DOCTOR_NAME = "Seuss";
    public static final String DEFAULT_NEXT_OF_KIN = "Ms Jane";
    public static final String DEFAULT_NOTES = "Goes to gym";
    public static final String DEFAULT_NEXT_OF_KIN_RELATIONSHIP = "Parent";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Symptom> symptoms;
    private Ic ic;
    private UrgencyLevel urgencyLevel;
    private NextOfKinPhone nextOfKinPhone;
    private DoctorName doctorName;
    private NextOfKin nextOfKin;
    private Notes notes;
    private NextOfKinRelationship nextOfKinRelationship;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        symptoms = new HashSet<>();
        ic = new Ic(DEFAULT_IC);
        urgencyLevel = new UrgencyLevel(DEFAULT_URGENCY_LEVEL);
        nextOfKinPhone = new NextOfKinPhone(DEFAULT_NEXT_OF_KIN_PHONE);
        doctorName = new DoctorName(DEFAULT_DOCTOR_NAME);
        nextOfKin = new NextOfKin(DEFAULT_NEXT_OF_KIN);
        notes = new Notes(DEFAULT_NOTES);
        nextOfKinRelationship = new NextOfKinRelationship(DEFAULT_NEXT_OF_KIN_RELATIONSHIP);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        symptoms = new HashSet<>(personToCopy.getSymptoms());
        ic = personToCopy.getIc();
        urgencyLevel = personToCopy.getUrgencyLevel();
        nextOfKinPhone = personToCopy.getNextOfKinPhone();
        doctorName = personToCopy.getDoctorName();
        nextOfKin = personToCopy.getNextOfKin();
        notes = personToCopy.getNotes();
        nextOfKinRelationship = personToCopy.getNextOfKinRelationship();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code symptoms} into a {@code Set<Symptom>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSymptoms(String ... symptoms) {
        this.symptoms = SampleDataUtil.getSymptomSet(symptoms);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Ic} of the {@code Person} that we are building.
     */
    public PersonBuilder withIc(String ic) {
        this.ic = new Ic(ic);
        return this;
    }

    /**
     * Sets the {@code UrgencyLevel} of the {@code Person} that we are building.
     */
    public PersonBuilder withUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = new UrgencyLevel(urgencyLevel);
        return this;
    }

    /**
     * Sets the {@code NextOfKinPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.nextOfKinPhone = new NextOfKinPhone(nextOfKinPhone);
        return this;
    }

    /**
     * Sets the {@code DoctorName} of the {@code Person} that we are building.
     */
    public PersonBuilder withDoctorName(String doctorName) {
        this.doctorName = new DoctorName(doctorName);
        return this;
    }

    /**
     * Sets the {@code NextOfKin} of the {@code Person} that we are building.
     */
    public PersonBuilder withNextOfKin(String nextOfKin) {
        this.nextOfKin = new NextOfKin(nextOfKin);
        return this;
    }

    /**
     * Sets the {@code NextOfKinRelationship} of the {@code Person} that we are building.
     */
    public PersonBuilder withNextOfKinRelationship(String nextOfKinRelationship) {
        this.nextOfKinRelationship = new NextOfKinRelationship(nextOfKinRelationship);
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Person} that we are building.
     */
    public PersonBuilder withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Builds and returns the {@code Person} object that we are building.
     */
    public Person build() {
        return new Person(name, phone, email, address, symptoms, ic, urgencyLevel,
                nextOfKinPhone, doctorName, nextOfKin, nextOfKinRelationship, notes);
    }


}
