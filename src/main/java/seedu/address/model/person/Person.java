package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.symptom.Symptom;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Ic ic;

    // Data fields
    private final Address address;
    private final Set<Symptom> symptoms = new HashSet<>();
    private final UrgencyLevel urgencyLevel;
    private final NextOfKinPhone nextOfKinPhone;
    private final DoctorName doctorName;
    private final NextOfKin nextOfKin;
    private final Notes notes;
    private final NextOfKinRelationship nextOfKinRelationship;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name,
                  Phone phone,
                  Email email,
                  Address address,
                  Set<Symptom> symptoms,
                  Ic ic,
                  UrgencyLevel urgencyLevel,
                  NextOfKinPhone nextOfKinPhone,
                  DoctorName doctorName,
                  NextOfKin nextOfKin,
                  NextOfKinRelationship nextOfKinRelationship,
                  Notes notes) {
        requireAllNonNull(name, phone, email, address, symptoms, ic,
                urgencyLevel, doctorName, nextOfKinPhone, nextOfKin, nextOfKinRelationship, notes);

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.symptoms.addAll(symptoms);
        this.ic = ic;
        this.urgencyLevel = urgencyLevel;
        this.nextOfKinPhone = nextOfKinPhone;
        this.doctorName = doctorName;
        this.nextOfKin = nextOfKin;
        this.nextOfKinRelationship = nextOfKinRelationship;
        this.notes = notes;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public NextOfKinPhone getNextOfKinPhone() {
        return nextOfKinPhone;
    }

    /**
     * Returns an immutable symptom set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Symptom> getSymptoms() {
        return Collections.unmodifiableSet(symptoms);
    }

    public Ic getIc() {
        return ic;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    public DoctorName getDoctorName() {
        return doctorName;
    }

    public NextOfKin getNextOfKin() {
        return nextOfKin;
    }

    public NextOfKinRelationship getNextOfKinRelationship() {
        return nextOfKinRelationship;
    }

    public Notes getNotes() {
        return notes;
    }

    /**
     * Returns true if both persons have the same ic.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getIc().equals(getIc());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && symptoms.equals(otherPerson.symptoms)
                && ic.equals(otherPerson.ic)
                && urgencyLevel.equals(otherPerson.urgencyLevel)
                && nextOfKinPhone.equals(otherPerson.nextOfKinPhone)
                && doctorName.equals(otherPerson.doctorName)
                && nextOfKin.equals(otherPerson.nextOfKin)
                && nextOfKinRelationship.equals(otherPerson.nextOfKinRelationship)
                && notes.equals(otherPerson.notes);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, symptoms,
                ic, urgencyLevel, doctorName, nextOfKinPhone, nextOfKin, nextOfKinRelationship, notes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("symptoms", symptoms)
                .add("ic", ic)
                .add("urgencyLevel", urgencyLevel)
                .add("nextOfKinPhone", nextOfKinPhone)
                .add("doctorName", doctorName)
                .add("nextOfKin", nextOfKin)
                .add("nextOfKinRelationship", nextOfKinRelationship)
                .add("notes", notes)
                .toString();
    }

}
