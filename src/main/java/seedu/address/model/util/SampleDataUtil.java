package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getSymptomSet("friends"), new Ic("S1234567A"), new UrgencyLevel("low"),
                    new NextOfKinPhone("81134232"), new DoctorName("Seuss"),
                    new NextOfKin("Bob"), new NextOfKinRelationship("Brother"), new Notes("Loves apples")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getSymptomSet("colleagues", "friends"), new Ic("S2345678A"), new UrgencyLevel("high"),
                    new NextOfKinPhone("81243522"), new DoctorName("James Tan"),
                    new NextOfKin("Kim"), new NextOfKinRelationship("Mother"),
                    new Notes("Loves Pokemon")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getSymptomSet("neighbours"), new Ic("S3456789A"), new UrgencyLevel("moderate"),
                    new NextOfKinPhone("91232456"), new DoctorName("Jun Jie"),
                    new NextOfKin("Naeya"), new NextOfKinRelationship("Sister"), new Notes("Hates Doritos")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getSymptomSet("family"), new Ic("S4567890A"), new UrgencyLevel("extreme"),
                    new NextOfKinPhone("91349234"), new DoctorName("Xiao Ming"),
                    new NextOfKin("Po"), new NextOfKinRelationship("Guardian"),
                    new Notes("Loves School Of Computing")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getSymptomSet("classmates"), new Ic("S5678901A"), new UrgencyLevel("low"),
                    new NextOfKinPhone("92352732"), new DoctorName("Li Ting"),
                    new NextOfKin("Johnny"), new NextOfKinRelationship("Brother"), new Notes("Not crazy")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getSymptomSet("colleagues"), new Ic("S6789012A"), new UrgencyLevel("high"),
                    new NextOfKinPhone("81234234"), new DoctorName("Xiao Jun"),
                    new NextOfKin("Ronny"), new NextOfKinRelationship("Grandparent"), new Notes("Crazy"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a symptom set containing the list of strings given.
     */
    public static Set<Symptom> getSymptomSet(String... strings) {
        return Arrays.stream(strings)
                .map(Symptom::new)
                .collect(Collectors.toSet());
    }

}
