package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Address;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ic;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.NextOfKinPhone;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UrgencyLevel;
import seedu.address.model.symptom.Symptom;

/**
 * Represents a command that deletes one or more people from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = "Usage:\n"
            + "        Single deletion: " + COMMAND_WORD + " INDEX\n"
            + "        Multiple deletion: " + COMMAND_WORD + " INDEX,INDEX[,INDEX,...]\n"
            + "        Range deletion: " + COMMAND_WORD + " START_INDEX-END_INDEX";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s): %1$s";
    public static final String MESSAGE_DELETE_FIELD_SUCCESS = "Deleted field(s) for Person(s): %1$s";

    public Set<Prefix> prefixes;

    public DeleteCommand(Set<Prefix> prefixes) {
        this.prefixes = prefixes;
    }

    public abstract Set<Index> getTargetIndicesAsSet();

    public Person getUpdatedPerson(Person personToDelete) {
        Name name = personToDelete.getName();
        Phone phone = personToDelete.getPhone();
        Email email = personToDelete.getEmail();
        Address address = personToDelete.getAddress();
        Set<Symptom> updatedSymptoms =
                prefixes.contains(CliSyntax.PREFIX_SYMPTOM) ? Set.of() : personToDelete.getSymptoms();
        Ic ic = personToDelete.getIc();
        UrgencyLevel urgencyLevel = personToDelete.getUrgencyLevel();
        NextOfKinPhone nextOfKinPhone = personToDelete.getNextOfKinPhone();
        DoctorName doctorName = personToDelete.getDoctorName();
        NextOfKin nextOfKin = personToDelete.getNextOfKin();
        Notes updatedNotes = prefixes.contains(CliSyntax.PREFIX_NOTES) ? new Notes("") : personToDelete.getNotes();
        return new Person(name, phone, email, address, updatedSymptoms, ic,
                 urgencyLevel, nextOfKinPhone, doctorName, nextOfKin, updatedNotes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return this.getTargetIndicesAsSet().equals(otherDeleteCommand.getTargetIndicesAsSet());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", this.getTargetIndicesAsSet())
                .toString();
    }
}
