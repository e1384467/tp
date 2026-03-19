package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PATIENT_NAME = new Prefix("pn/");
    public static final Prefix PREFIX_PATIENT_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_SYMPTOM = new Prefix("s/");
    public static final Prefix PREFIX_IC = new Prefix("ic/");
    public static final Prefix PREFIX_URGENCY = new Prefix("u/");
    public static final Prefix PREFIX_NEXT_OF_KIN_PHONE = new Prefix("nkp/");
    public static final Prefix PREFIX_DOCTOR = new Prefix("d/");
}
