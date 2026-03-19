package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_OF_KIN_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URGENCY;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.symptom.Symptom;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_PATIENT_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PATIENT_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getSymptoms().stream().forEach(
            s -> sb.append(PREFIX_SYMPTOM + s.symptomName + " ")
        );
        sb.append(PREFIX_IC + person.getIc().value + " ");
        sb.append(PREFIX_URGENCY + person.getUrgencyLevel().toString() + " ");
        sb.append(PREFIX_NEXT_OF_KIN_PHONE + person.getNextOfKinPhone().toString() + " ");
        sb.append(PREFIX_DOCTOR + person.getDoctorName().toString() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getUpdatePersonDescriptorDetails(UpdatePersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_PATIENT_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PATIENT_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getSymptoms().isPresent()) {
            Set<Symptom> symptoms = descriptor.getSymptoms().get();
            if (symptoms.isEmpty()) {
                sb.append(PREFIX_SYMPTOM).append(" ");
            } else {
                symptoms.forEach(s -> sb.append(PREFIX_SYMPTOM).append(s.symptomName).append(" "));
            }
        }
        descriptor.getIc().ifPresent(ic -> sb.append(PREFIX_IC).append(ic.value).append(" "));
        descriptor.getUrgencyLevel().ifPresent(urgencyLevel -> sb.append(PREFIX_URGENCY).append(urgencyLevel)
                .append(" "));
        descriptor.getNextOfKinPhone().ifPresent(nextOfKinPhone -> sb.append(PREFIX_NEXT_OF_KIN_PHONE)
                .append(nextOfKinPhone.value).append(" "));
        descriptor.getDoctorName().ifPresent(doctorName -> sb.append(PREFIX_DOCTOR).append(doctorName)
                .append(" "));
        return sb.toString();
    }
}
