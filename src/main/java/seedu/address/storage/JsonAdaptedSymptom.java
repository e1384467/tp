package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.symptom.Symptom;

/**
 * Jackson-friendly version of {@link Symptom}.
 */
class JsonAdaptedSymptom {

    private final String symptomName;

    /**
     * Constructs a {@code JsonAdaptedSymptom} with the given {@code symptomName}.
     */
    @JsonCreator
    public JsonAdaptedSymptom(String symptomName) {
        this.symptomName = symptomName;
    }

    /**
     * Converts a given {@code Symptom} into this class for Jackson use.
     */
    public JsonAdaptedSymptom(Symptom source) {
        symptomName = source.symptomName;
    }

    @JsonValue
    public String getSymptomName() {
        return symptomName;
    }

    /**
     * Converts this Jackson-friendly adapted symptom object into the model's {@code Symptom} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted symptom.
     */
    public Symptom toModelType() throws IllegalValueException {
        if (!Symptom.isValidSymptomName(symptomName)) {
            throw new IllegalValueException(Symptom.MESSAGE_CONSTRAINTS);
        }
        return new Symptom(symptomName);
    }

}
