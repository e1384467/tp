package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPEND_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_OF_KIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXT_OF_KIN_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SYMPTOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URGENCY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Updates the details of an existing person in the address book.
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PATIENT_NAME + "NAME] "
            + "[" + PREFIX_PATIENT_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_IC + "IC] "
            + "[" + PREFIX_URGENCY + "LEVEL] "
            + "[" + PREFIX_NEXT_OF_KIN + "NEXT-OF-KIN] "
            + "[" + PREFIX_NEXT_OF_KIN_PHONE + "N-O-K PHONE] "
            + "[" + PREFIX_DOCTOR + "]"
            + "[" + PREFIX_SYMPTOM + "SYMPTOM]"
            + "[" + PREFIX_NOTES + "NOTES] [" + PREFIX_APPEND_NOTES + "APPEND_NOTES]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PATIENT_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_UPDATE_PERSON_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "At least one field to update must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final UpdatePersonDescriptor updatePersonDescriptor;
    private Person originalPerson;
    private Person updatedPersonForUndo;
    private boolean wasExecuted = false;

    /**
     * @param index of the person in the filtered person list to edit
     * @param updatePersonDescriptor details to edit the person with
     */
    public UpdateCommand(Index index, UpdatePersonDescriptor updatePersonDescriptor) {
        requireNonNull(index);
        requireNonNull(updatePersonDescriptor);

        this.index = index;
        this.updatePersonDescriptor = new UpdatePersonDescriptor(updatePersonDescriptor);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        originalPerson = personToUpdate;
        Person updatedPerson = createUpdatedPerson(personToUpdate, updatePersonDescriptor);
        updatedPersonForUndo = updatedPerson;

        if (!personToUpdate.isSamePerson(updatedPerson) && model.hasPerson(updatedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        wasExecuted = true;
        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public void undo(Model model) throws CommandException {
        requireNonNull(model);
        if (wasExecuted && updatedPersonForUndo != null && originalPerson != null) {
            model.setPerson(updatedPersonForUndo, originalPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createUpdatedPerson(Person personToUpdate, UpdatePersonDescriptor updatePersonDescriptor)
            throws CommandException {
        assert personToUpdate != null;

        Name updatedName = updatePersonDescriptor.getName().orElse(personToUpdate.getName());
        Phone updatedPhone = updatePersonDescriptor.getPhone().orElse(personToUpdate.getPhone());
        Email updatedEmail = updatePersonDescriptor.getEmail().orElse(personToUpdate.getEmail());
        Address updatedAddress = updatePersonDescriptor.getAddress().orElse(personToUpdate.getAddress());
        Set<Symptom> updatedSymptoms = updatePersonDescriptor.getSymptoms().orElse(personToUpdate.getSymptoms());
        Ic updatedIc = updatePersonDescriptor.getIc().orElse(personToUpdate.getIc());
        UrgencyLevel updatedUrgencyLevel = updatePersonDescriptor.getUrgencyLevel()
                .orElse(personToUpdate.getUrgencyLevel());
        NextOfKinPhone updatedNextOfKinPhone = updatePersonDescriptor.getNextOfKinPhone()
                .orElse(personToUpdate.getNextOfKinPhone());
        DoctorName updatedDoctorName = updatePersonDescriptor.getDoctorName().orElse(personToUpdate.getDoctorName());
        NextOfKin updatedNextOfKin = updatePersonDescriptor.getNextOfKin().orElse(personToUpdate.getNextOfKin());

        // NEW: Abstracted Append Logic
        Notes updatedNotes = personToUpdate.getNotes();

        if (updatePersonDescriptor.getNotes().isPresent()) {
            updatedNotes = updatePersonDescriptor.getNotes().get();
        } else if (updatePersonDescriptor.getNotesToAppend().isPresent()) {
            try {
                // The combination logic is now entirely handled by the Notes class
                updatedNotes = updatedNotes.append(updatePersonDescriptor.getNotesToAppend().get());
            } catch (IllegalArgumentException e) {
                // Catches the error if the appended notes exceed character limits
                throw new CommandException("Appending this text exceeds the note character constraints. "
                        + Notes.MESSAGE_CONSTRAINTS);
            }
        }

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedSymptoms,
                updatedIc, updatedUrgencyLevel, updatedNextOfKinPhone, updatedDoctorName,
                updatedNextOfKin, updatedNotes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UpdateCommand otherUpdateCommand)) {
            return false;
        }

        return index.equals(otherUpdateCommand.index)
                && updatePersonDescriptor.equals(otherUpdateCommand.updatePersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", updatePersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class UpdatePersonDescriptor {
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
        private Notes notesToAppend; // CHANGED to Notes

        public UpdatePersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code symptoms} is used internally.
         */
        public UpdatePersonDescriptor(UpdatePersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setSymptoms(toCopy.symptoms);
            setUrgencyLevel(toCopy.urgencyLevel);
            setIc(toCopy.ic);
            setNextOfKinPhone(toCopy.nextOfKinPhone);
            setDoctorName(toCopy.doctorName);
            setNextOfKin(toCopy.nextOfKin);
            setNotes(toCopy.notes);
            setNotesToAppend(toCopy.notesToAppend); // Updated
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address,
                    symptoms, urgencyLevel, ic, nextOfKinPhone, doctorName, nextOfKin, notes, notesToAppend);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setIc(Ic ic) {
            this.ic = ic;
        }

        public Optional<Ic> getIc() {
            return Optional.ofNullable(ic);
        }

        public void setDoctorName(DoctorName doctorName) {
            this.doctorName = doctorName;
        }

        public Optional<DoctorName> getDoctorName() {
            return Optional.ofNullable(doctorName);
        }

        public void setNextOfKin(NextOfKin nextOfKin) {
            this.nextOfKin = nextOfKin;
        }

        public Optional<NextOfKin> getNextOfKin() {
            return Optional.ofNullable(nextOfKin);
        }

        public void setNotes(Notes notes) {
            this.notes = notes;
        }

        public Optional<Notes> getNotes() {
            return Optional.ofNullable(notes);
        }

        // UPDATED: Now accepts and returns Notes
        public void setNotesToAppend(Notes notesToAppend) {
            this.notesToAppend = notesToAppend;
        }

        public Optional<Notes> getNotesToAppend() {
            return Optional.ofNullable(notesToAppend);
        }

        public void setSymptoms(Set<Symptom> symptoms) {
            this.symptoms = (symptoms != null) ? new HashSet<>(symptoms) : null;
        }

        public Optional<Set<Symptom>> getSymptoms() {
            return (symptoms != null) ? Optional.of(Collections.unmodifiableSet(symptoms)) : Optional.empty();
        }

        public void setUrgencyLevel(UrgencyLevel urgencyLevel) {
            this.urgencyLevel = urgencyLevel;
        }

        public Optional<UrgencyLevel> getUrgencyLevel() {
            return Optional.ofNullable(urgencyLevel);
        }

        public void setNextOfKinPhone(NextOfKinPhone nextOfKinPhone) {
            this.nextOfKinPhone = nextOfKinPhone;
        }

        public Optional<NextOfKinPhone> getNextOfKinPhone() {
            return Optional.ofNullable(nextOfKinPhone);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof UpdatePersonDescriptor otherUpdatePersonDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherUpdatePersonDescriptor.name)
                    && Objects.equals(phone, otherUpdatePersonDescriptor.phone)
                    && Objects.equals(email, otherUpdatePersonDescriptor.email)
                    && Objects.equals(address, otherUpdatePersonDescriptor.address)
                    && Objects.equals(symptoms, otherUpdatePersonDescriptor.symptoms)
                    && Objects.equals(ic, otherUpdatePersonDescriptor.ic)
                    && Objects.equals(urgencyLevel, otherUpdatePersonDescriptor.urgencyLevel)
                    && Objects.equals(nextOfKinPhone, otherUpdatePersonDescriptor.nextOfKinPhone)
                    && Objects.equals(doctorName, otherUpdatePersonDescriptor.doctorName)
                    && Objects.equals(nextOfKin, otherUpdatePersonDescriptor.nextOfKin)
                    && Objects.equals(notes, otherUpdatePersonDescriptor.notes)
                    && Objects.equals(notesToAppend, otherUpdatePersonDescriptor.notesToAppend);
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
                    .add("notes", notes)
                    .add("notesToAppend", notesToAppend)
                    .toString();
        }
    }
}
