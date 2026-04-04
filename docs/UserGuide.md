---
layout: default.md
title: "User Guide"
pageNav: 3
---

# ClinicConnect User Guide

ClinicConnect is a **desktop app for triage coordinators to manage patient records, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, ClinicConnect can get your triage tasks done faster than traditional GUI apps.

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `17` or above installed in your Computer.
2. Download the latest `ClinicConnect.jar` file from our releases page.
3. Copy the file to the folder you want to use as the home folder for your clinic's database.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ClinicConnect.jar` command to run the application.<br>
   A GUI should appear in a few seconds. Note how the app contains some sample triage data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` : Lists all patients.
    * `add pn/John Doe p/98765432 e/johnd@example.com a/311, Clementi Ave 2, #02-25 ic/S1234567A u/high nk/John nkp/91234567 nkr/Brother d/Dr Sally s/fever s/cough n/Does not like to eat veggies` : Adds a patient named `John Doe` to ClinicConnect.
    * `update 1 u/high s/Severe chest pain` : Updates the urgency and symptoms of the 1st patient shown in the current list.
    * `find pn/John` : Searches for patients with the name "John".
    * `delete 1` : Deletes the 1st patient shown in the current list.
    * `exit` : Exits the app safely and saves your data.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br/>
  e.g. in `add pn/NAME`, `NAME` is a parameter which can be used as `add pn/John Doe`.
* Items in square brackets are optional.<br/>
  e.g `[n/NOTES]` can be used as `n/Patient has history of asthma` or simply left out.
* Parameters can be in any order.<br/>
  e.g. if the command specifies `pn/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER pn/NAME` is also acceptable.
* All commands and prefixes are case-insensitive.
* Leading and trailing spaces are ignored/trimmed automatically.
* Internal spaces inside a command or prefix are not allowed and will be rejected.

</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

**Format:** `help`

### Adding a patient: `add`

Records comprehensive patient information (name, identification, contact details, medical urgency, and notes) and saves it to the hard disk.

**Format:** `add pn/<PATIENT NAME> ic/<IC NUMBER> p/<PATIENT PHONE NUMBER> u/<URGENCY LEVEL> d/<DOCTOR NAME> nk/<NEXT-OF-KIN NAME> nkp/<NEXT-OF-KIN PHONE NUMBER> nkr/<NEXT-OF-KIN RELATIONSHIP> [s/<SYMPTOMS>] [n/<NOTES>]`

**Compulsory Fields & Constraints:**
* **`pn/`, `d/`, `nk/` (Names):** May contain letters (A-Z, a-z), spaces, commas (,), hyphens (-), apostrophes ('), and periods (.).
* **`ic/`:** Must follow the format: `[S/T/F/G/M]` + 7 digits + 1 letter (total 9 characters). Duplicates are not allowed.
* **`p/`, `nkp/` (Phones):** Must contain exactly 8 digits (0-9 only).
* **`u/` (Urgency):** Must be exactly one of: `low`, `moderate`, `high`, `extreme`.
* **`nkr/` (Next of kin relationship):** May contain letters (A-Z, a-z), spaces, commas (,), hyphens (-), apostrophes ('), and periods (.).
* 
**Optional Fields:**
* **`s/` (Symptoms):** Must be alphanumeric.
* **`n/` (Notes):** Maximum length of 500 characters.

**Tip:** A person can have any number of symptoms (including 0).

**Examples:**
* `add pn/John Doe Jun Kai ic/T0123456B p/12345678 u/high d/Dr Tan Ah Beng nk/Mary Doe nkp/87654321 nkr/Mother s/Diabetic n/Admitted at 12pm`

### Listing all patients : `list`

Displays all patients in the application in a structured list format. You can also filter the list by urgency level and/or symptoms.

**Format:** `list [u/<URGENCY_LEVEL>]... [s/<SYMPTOM>]...`

* You may provide `u/` (urgency level) to match urgency levels.
* You may provide `s/` (symptoms) to match symptoms.
* If both `u/` and `s/` are provided, only patients matching **both** criteria are shown.

**Examples:**
* `list` (shows everyone)
* `list u/high`
* `list s/fever s/cough`
* `list u/high s/fever`

### Updating a patient : `update`

Updates an existing patient's details in ClinicConnect.

**Format:** `update <INDEX> [pn/<PATIENT NAME>] [ic/<IC NUMBER>] [p/<PATIENT PHONE NUMBER>] [s/<SYMPTOMS>] [u/<URGENCY LEVEL>] [d/<DOCTOR NAME>] [nk/<NEXT-OF-KIN NAME>] [nkp/<NEXT-OF-KIN PHONE NUMBER>] [nkr/<NEXT-OF-KIN RELATIONSHIP>] [n/<NOTES>]`

* Edits the patient at the specified `INDEX`. The index refers to the index number shown in the displayed patient list. The index **must be a positive integer** (1, 2, 3, …).
* **At least one** of the optional fields must be provided.
* Existing values will be overwritten by the input values.
* When editing symptoms, the existing symptoms will be removed (not cumulative). You can remove all symptoms by typing `s/` without specifying any symptoms after it.
* The same validation constraints from the `add` command apply here.

**Examples:**
* `update 1 p/91234567` Updates the phone number of the 1st patient to be `91234567`.
* `update 2 u/extreme s/Cardiac arrest` Updates the urgency level of the 2nd patient to `extreme` and symptoms to `Cardiac arrest`.

### Searching for a patient : `find`

Allows triage coordinators to locate specific patient records using various identifiers, reducing the manual effort of scrolling.

**Format:** `find [pn/<NAME>] [ic/<IC NUMBER>] [p/<PATIENT PHONE NUMBER>] [e/<EMAIL>] [d/<DOCTOR NAME>]`

* Finds patients whose identifiers match the given keywords.
* At least one identifier must be provided.
* The search is case-insensitive.
* Leading and trailing spaces are ignored/trimmed.
* Only full words will be matched for names (e.g., `Han` will not match `Hans`).
* Patients matching at least one keyword will be returned (i.e. `OR` search).

**Examples:**
* `find pn/Alice` returns patients with the name Alice.
* `find ic/S1234567A` returns the patient with that specific IC.
* `find e/johndoe@example.com` returns the patient(s) with that email.
* `find d/Dr Sally` returns the patient(s) with that doctor name.

### Deleting a patient : `delete`

Permanently removes patient records from ClinicConnect. Deletion is irreversible.

**Format:**
* **Single deletion:** `delete <INDEX>`
* **Multiple deletion:** `delete <INDEX>,<INDEX>,<INDEX>`
* **Range deletion:** `delete <START_INDEX>-<END_INDEX>`

* The index **must be a positive integer**.
* For multiple deletions, use a comma (`,`) delimiter. Duplicated indices (e.g., `delete 2,2`) will be rejected.
* For range deletions, use a hyphen (`-`) delimiter. The start index must be less than or equal to the end index.

**Examples:**
* `delete 2` deletes the 2nd person in the patient records.
* `delete 1,3,5` deletes the 1st, 3rd, and 5th persons.
* `delete 1-4` deletes the 1st through 4th persons.

### Clearing all entries : `clear`

Clears all entries from the clinic records.

**Format:** `clear`

### Exiting safely : `exit`

Exits the application and saves all data to the hard disk.

**Format:** `exit`
* The command does not accept any additional parameters (e.g., `exit now` will be rejected).

--------------------------------------------------------------------------------------------------------------------

## Automatic Triage Sorting

ClinicConnect automatically sorts the patient list to prioritize critical cases:
1.  **Urgency Level:** Patients are ranked `EXTREME` > `HIGH` > `MODERATE` > `LOW`.
2.  **Tie-Breaker:** If two patients have the exact same urgency level, they are sorted deterministically by their **IC Number**.

--------------------------------------------------------------------------------------------------------------------

## Command History navigation using the Up/Down arrow keys

ClinicConnect allows you to navigate through your command history using the `Up` and `Down` arrow keys when the command box is focused (_i.e. when there is a blinking vertical line in the command box_). This feature enables you to quickly recall and reuse previous commands without retyping them.

Only the commands that were successfully executed (_i.e. the command box does not light up red after you press the `enter` key_) are stored in the command history. Those commands will have their trailing and leading whitespaces trimmed before storing in the command history. When you press the `Up` arrow key, you will see the most recent command you entered. Pressing it again will take you further back in your command history. Conversely, pressing the `Down` arrow key will move you forward through the command history.

The command history is session-based, meaning that it is cleared when you exit the application. Therefore, only commands entered during the current session will be available for navigation using the Up and Down arrow keys.

The command history removes duplicated commands, so if you enter the same command multiple times, only the most recent instance will be stored in the history. This helps to keep the command history concise and relevant. Duplicated commands are determined based on their trimmed version and compared in a case-insensitive manner. Commands are considered duplicates only if they are exactly the same after normalization (i.e. trimming leading and trailing whitespaces and ignoring letter casing).

Examples of how duplicated commands are handled in the command history:

| Commands about to enter                                       | Command in command history | Is it considered a duplicate? | Why?                                                                                                                                            |
|---------------------------------------------------------------|----------------------------|-----|-------------------------------------------------------------------------------------------------------------------------------------------------|
| `[LEADING_WHITESPACES] delete 1 s/ n/ [TRAILING_WHITESPACES]` | `delete 1 s/ n/`           | Yes | After trimming leading and trailing whitespaces, the command is identical to the existing command in the history (case-insensitive comparison). |
| `DELETE 1 S/ N/`                                              | `delete 1 s/ n/`           | Yes | Case-insensitive comparison, the command is identical to the existing command in the history.                                                   |
| `delete 1 n/ s/`                                              | `delete 1 s/ n/`           | No  | Althought the both commands are functionally the same, they are not be treated as duplicates as they are not identical                          |

The command history navigation feature enhances your efficiency by allowing you to quickly access and reuse previously entered commands, saving you time and effort in managing patient records with ClinicConnect.

--------------------------------------------------------------------------------------------------------------------

## Data Management

### Saving the data

ClinicConnect data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClinicConnect data is saved automatically as a JSON file `[JAR file location]/data/clinicconnect.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file make its format invalid, ClinicConnect will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause ClinicConnect to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClinicConnect home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| Action | Format                                                                                     | Examples                                                                   |
| :--- |:-------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------|
| **Add** | `add pn/NAME ic/IC p/PHONE u/URGENCY d/DOCTOR nk/NOK nkp/NOK_PHONE [s/SYMPTOMS] [n/NOTES]` | `add pn/John ic/S1234567A p/98765432 u/high d/Dr Tan nk/Mary nkp/87654321` |
| **Update** | `update INDEX [prefix/VALUE]...`                                                           | `update 1 u/extreme n/Immediate surgery required`                          |
| **Search** | `find [pn/NAME] [ic/IC] [p/PHONE] [e/EMAIL] [d/DOCTOR]`                                     | `find e/johndoe@example.com`, `find d/Dr Sally`, `find ic/S1234567A`                                         |
| **Delete** | `delete INDEX` <br> `delete INDEX,INDEX` <br> `delete START-END`                           | `delete 3` <br> `delete 1,4` <br> `delete 2-5`                             |
| **List** | `list [u/<URGENCY_LEVEL>]... [s/<SYMPTOM>]...`                                               | `list` <br> `list u/high` <br> `list s/fever s/cough`                     |
| **Clear** | `clear`                                                                                    | `clear`                                                                    |
| **Exit** | `exit`                                                                                     | `exit`                                                                     |
