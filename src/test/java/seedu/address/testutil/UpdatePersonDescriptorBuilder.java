package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;
import seedu.address.model.symptom.Symptom;

/**
 * A utility class to help with building UpdatePersonDescriptor objects.
 */
public class UpdatePersonDescriptorBuilder {

    private final UpdatePersonDescriptor descriptor;

    public UpdatePersonDescriptorBuilder() {
        descriptor = new UpdatePersonDescriptor();
    }

    public UpdatePersonDescriptorBuilder(UpdatePersonDescriptor descriptor) {
        this.descriptor = new UpdatePersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdatePersonDescriptor} with fields containing {@code person}'s details
     */
    public UpdatePersonDescriptorBuilder(Person person) {
        descriptor = new UpdatePersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setSymptoms(person.getSymptoms());
        descriptor.setIc(person.getIc());
        descriptor.setUrgencyLevel(person.getUrgencyLevel());
    }

    /**
     * Sets the {@code Name} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code symptoms} into a {@code Set<Symptom>} and set it to the {@code UpdatePersonDescriptor}
     * that we are building.
     */
    public UpdatePersonDescriptorBuilder withSymptoms(String... symptoms) {
        Set<Symptom> symptomSet = Stream.of(symptoms).map(Symptom::new).collect(Collectors.toSet());
        descriptor.setSymptoms(symptomSet);
        return this;
    }

    /**
     * Sets the {@code Ic} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withIc(String ic) {
        descriptor.setIc(new Ic(ic));
        return this;
    }

    /**
     * Sets the {@code UrgencyLevel} of the {@code UpdatePersonDescriptor} that we are building.
     */
    public UpdatePersonDescriptorBuilder withUrgencyLevel(String urgencyLevel) {
        descriptor.setUrgencyLevel(new UrgencyLevel(urgencyLevel));
        return this;
    }

    public UpdatePersonDescriptor build() {
        return descriptor;
    }
}
