package seedu.address.model.symptom;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SymptomTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Symptom(null));
    }

    @Test
    public void constructor_invalidSymptomName_throwsIllegalArgumentException() {
        String invalidSymptomName = "";
        assertThrows(IllegalArgumentException.class, () -> new Symptom(invalidSymptomName));
    }

    @Test
    public void isValidSymptomName() {
        // null symptom name
        assertThrows(NullPointerException.class, () -> Symptom.isValidSymptomName(null));
    }

}
