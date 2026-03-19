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
    private Label nextOfKinPhone;
    @FXML
    private FlowPane symptoms;
    @FXML
    private Label doctorName;
    @FXML
    private Label nextOfKin;
    @FXML
    private Label notes;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     * @param person The person object containing clinical and personal data.
     * @param displayedIndex The index of the person in the filtered list.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText("Hp: " + person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        nextOfKinPhone.setText("NOK phone: " + person.getNextOfKinPhone().toString());
        doctorName.setText("Doctor: " + person.getDoctorName().toString());
        nextOfKin.setText("NOK: " + person.getNextOfKin().toString());

        // Map the new medical fields to the UI
        ic.setText("NRIC: " + person.getIc().value);

        // Clinical Details: Apply styling using the refactored method
        setUrgencyStyle(person.getUrgencyLevel().getStyleClass(), person.getUrgencyLevel().toString());

        person.getSymptoms().stream()
                .sorted(Comparator.comparing(symptom -> symptom.symptomName))
                .forEach(symptom -> symptoms.getChildren().add(new Label(symptom.symptomName)));

        notes.setText("Notes: " + person.getNotes().getValue());
    }

    /**
     * Sets the text and dynamic CSS styling for the urgency level label.
     * This method follows the "Single Responsibility" principle by taking only the
     * specific data strings needed for styling.
     * @param styleClass The CSS class to apply (e.g., "urgency-high").
     * @param displayText The raw level string to display (e.g., "HIGH").
     */
    private void setUrgencyStyle(String styleClass, String displayText) {
        // Format display text for better UI readability (e.g., "EXTREME" -> "Extreme")
        String formattedText = displayText.substring(0, 1).toUpperCase()
                + displayText.substring(1).toLowerCase();
        urgencyLevel.setText(formattedText);

        // Reset style classes to ensure clean state during JavaFX cell reuse
        urgencyLevel.getStyleClass().clear();

        // "label" is the base JavaFX style; "urgency-badge" is your custom shared styling
        urgencyLevel.getStyleClass().addAll("label", "urgency-badge", styleClass);
    }
}
