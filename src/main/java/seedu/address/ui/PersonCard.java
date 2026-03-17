package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label ic;
    @FXML
    private Label urgencyLevel;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText("Hp: " + person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        // Map the new medical fields to the UI
        ic.setText("NRIC: " + person.getIc().value);

        // Clinical Details: Urgency Level styling and text
        setUrgencyStyle(person);

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Sets the text and dynamic CSS styling for the urgency level label.
     */
    private void setUrgencyStyle(Person person) {
        String urgencyValue = person.getUrgencyLevel().getValue().toLowerCase();

        // Set display text with first letter capitalized (e.g., "Moderate")
        urgencyLevel.setText(urgencyValue.substring(0, 1).toUpperCase() + urgencyValue.substring(1));

        // Clear existing style classes to prevent color stacking when cells are reused in ListView
        urgencyLevel.getStyleClass().clear();

        // Add back the base 'label' style plus our custom urgency styling classes
        urgencyLevel.getStyleClass().addAll(
                "label",
                "urgency-badge",
                "urgency-" + urgencyValue
        );
    }
}
