package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKinPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;
import seedu.address.model.symptom.Symptom;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedSymptom> symptoms = new ArrayList<>();
    private final String ic;
    private final String urgencyLevel;
    private final String nextOfKinPhone;
    private final String doctorName;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedSymptom> tags, @JsonProperty("ic") String ic,
            @JsonProperty("urgencyLevel") String urgencyLevel,
                             @JsonProperty("doctorName") String doctorName,
                             @JsonProperty("nextOfKinPhone") String nextOfKinPhone) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (symptoms != null) {
            this.symptoms.addAll(symptoms);
        }
        this.ic = ic;
        this.urgencyLevel = urgencyLevel;
        this.nextOfKinPhone = nextOfKinPhone;
        this.doctorName = doctorName;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        symptoms.addAll(source.getSymptoms().stream()
                .map(JsonAdaptedSymptom::new)
                .collect(Collectors.toList()));
        ic = source.getIc().value;
        urgencyLevel = source.getUrgencyLevel().toString();
        nextOfKinPhone = source.getNextOfKinPhone().toString();
        doctorName = source.getDoctorName().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Symptom> personSymptoms = new ArrayList<>();
        for (JsonAdaptedSymptom symptom : symptoms) {
            personSymptoms.add(symptom.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (ic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Ic.class.getSimpleName()));
        }
        if (!Ic.isValidIc(ic)) {
            throw new IllegalValueException(Ic.MESSAGE_CONSTRAINTS);
        }
        final Ic modelIc = new Ic(ic);

        if (urgencyLevel == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UrgencyLevel.class.getSimpleName()));
        }
        if (!UrgencyLevel.isValidUrgencyLevel(urgencyLevel)) {
            throw new IllegalValueException(UrgencyLevel.MESSAGE_CONSTRAINTS);
        }
        final UrgencyLevel modelUrgencyLevel = new UrgencyLevel(urgencyLevel);

        if (nextOfKinPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NextOfKinPhone.class.getSimpleName()));
        }
        if (!NextOfKinPhone.isValidNextOfKinPhone(nextOfKinPhone)) {
            throw new IllegalValueException(NextOfKinPhone.MESSAGE_CONSTRAINTS);
        }
        final NextOfKinPhone modelNextOfKinPhone = new NextOfKinPhone(nextOfKinPhone);
        if (doctorName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DoctorName.class.getSimpleName()));
        }
        if (!DoctorName.isValidName(doctorName)) {
            throw new IllegalValueException(DoctorName.MESSAGE_CONSTRAINTS);
        }
        final DoctorName modelDoctorName = new DoctorName(doctorName);

        final Set<Symptom> modelSymptoms = new HashSet<>(personSymptoms);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelSymptoms, modelIc, modelUrgencyLevel,
                modelNextOfKinPhone, modelDoctorName);
    }

}
