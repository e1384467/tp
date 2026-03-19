---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# ClinicConnect Developer Guide

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Automatic Triage Sorting

The Automatic Triage Sorting mechanism ensures that the patient list is always ordered by medical severity, allowing triage coordinators to identify critical cases immediately.

This feature is facilitated by the `Urgency` and `Ic` classes, and is integrated directly into the `Person` model. The sorting logic relies on Java's `Comparable<T>` interface to enforce a strict, deterministic order every time the list of patients is modified (e.g., during an `add` or `update` command).

<box type="info" seamless>

**Note:** The sorting logic comparison can be conceptually represented by the following mathematical formula:
`Triage Priority = (Urgency Weight * 10) + IC Lexicographical Value`
</box>

#### Sorting Logic Flow
1. **Primary Sort (Urgency):** The `Urgency` class implements `Comparable<Urgency>`. It assigns internal weightages to its valid states (`EXTREME` = 4, `HIGH` = 3, `MODERATE` = 2, `LOW` = 1). When two patients are compared, the one with the higher urgency weight is placed higher in the list.
2. **Secondary Sort (Tie-Breaker):** If two patients have the exact same `Urgency`, the `Person#compareTo()` method falls back to comparing their `Ic` objects. The `Ic` class implements `Comparable<Ic>` and uses standard lexicographical string comparison to ensure a consistent, deterministic order.

#### Class Diagram
The following class diagram shows how the `Comparable` interface is implemented across the domain models:

<img src="images/TriageSortingClassDiagram.png" width="450" />

#### Sequence of Events
When a user executes a command that alters the patient list (like `add` or `update`), the internal list must re-sort itself.
1. The `LogicManager` executes the command, which calls `Model#addPerson(Person)`.
2. The `ModelManager` adds the patient to the `UniquePersonList`.
3. The `UniquePersonList` internal list (or a wrapping `SortedList` in the UI) triggers the `Person#compareTo(Person)` method.
4. The list is re-ordered and the UI updates automatically to reflect the new triage hierarchy.

#### Design Considerations: Triage Sorting

**Aspect: How to maintain the sorted order of the patient list.**

* **Alternative 1 (Current Implementation):** Implement `Comparable` in `Person` and enforce sorting at the `UniquePersonList` level or UI `SortedList` level automatically upon any modification.
    * *Pros:* The UI and internal data are always inherently synchronized. No explicit `sort` command needs to be called by the user, fitting the fast-paced triage environment.
    * *Cons:* Slight performance overhead since sorting occurs on every single addition or update (O(n log n) time complexity per modification).
* **Alternative 2:** Do not sort automatically. Create a separate `sort` command that the user must type manually.
    * *Pros:* Better performance during rapid, sequential `add` operations.
    * *Cons:* Highly dangerous in a medical triage context. If a user forgets to type `sort`, an `EXTREME` urgency patient might be left at the bottom of the list, leading to critical delays in care.

**Reason for choosing Alternative 1:** In a clinical setting, data accuracy and immediate visibility of critical patients strictly outweigh the minor performance cost of automatic sorting. "Security by Design" principles dictate that the system should fail-safe; automatically sorting ensures no critical patient is ever accidentally hidden.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add pn/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add pn/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of patient contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: This product eliminates the administrative friction of navigating complex, full-scale medical
record systems during time-sensitive triage. By providing a streamlined, high-speed CLI interface for tracking patient
updates and doctor assignments, this reduces coordinators' cognitive load, ensuring faster patient throughput and more
accurate prioritisation in fast-paced, high-workload clinical environments.

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​            | I want to …​                                                   | So that I can…​                                                                 |
|----------|--------------------|----------------------------------------------------------------|---------------------------------------------------------------------------------|
| `* * *`  | beginner user      | list all patients in the system                                | verify that the data I just entered was saved correctly.                        |
| `* * *`  | beginner user      | exit the app safely                                            | be assured that the data entered into the app is saved when I end my shift.     |                                                                    |
| `* * *`  | user               | add patient details                                            | start tracking those patients.                                                  |
| `* * *`  | triage coordinator | update specific details of a patient (such as their urgency level, symptoms, or notes) | quickly keep records current as the patient moves through the triage process and their medical condition changes. |
| `* * *`  | user               | remove patients                                                | remove duplicates or wrongly created records to keep it accurate.               |
| `* *`    | triage coordinator | search for patient records using different criteria (like Name, IC, Phone, or Urgency Level) | easily locate a specific patient in an emergency or find specific groups of patients without manually scrolling through the entire database. |
| `* *`    | expert user        | use my up/down keyboard keys                                   | conveniently access previously entered commands in my history to execute again. |
| `* *`    | expert user        | archive old patient records                                    | focus on the relevant patient records.                                          |
| `* *`    | beginner user      | see a confirmation message after adding a patient              | be assured that a patient has been added to my contacts.                        |
| `* *`    | beginner user      | use a help command                                             | easily understand the capabilities of ClinicConnect.                            |
| `* *`    | beginner user      | reset the app to its initial state                             | safely experiment with the app.                                                 |
| `* *`    | beginner user      | see instructions to help me correct my mistakes                | easily correct mistakes.                                                        |
| `* *`    | user               | sort patients based on their urgency level                     | quickly identify which patient requires immediate care.                         |
| `* *`    | expert user        | chain commands together in a single line (e.g., add … && list) | execute multiple actions without waiting for intermediate prompts.              |
| `* *`    | user               | attach notes to patient contacts                               | capture contextual information.                                                 |
| `* *`    | triage coordinator | perform batch edits or deletions                               | manage multiple patients efficiently.                                           |
| `* *`    | user               | use shorter prefixes                                           | get things done quickly.                                                        |
| `* *`    | user               | add an urgency level to each patient                           | know which patient requires immediate attention first.                          |
| `* *`    | expert user        | use a one-line command format (e.g., add n/ p/ ic/ cond/)      | enter a patient's full clinical profile quickly in seconds.                     |
| `*`      | beginner user      | undo a mistaken deletion from an entered command               | recover accidentally deleted data without re-entering everything.               |
| `*`      | beginner user      | skip onboarding steps                                          | explore the application freely.                                                 |
| `*`      | beginner user      | see the app populated with sample data                         | easily see how the app will look when it is in use.                             |
| `*`      | beginner user      | cancel an action midway                                        | stay in control.                                                                |
| `*`      | beginner user      | view a brief onboarding walkthrough                            | know what the app can do.                                                       |
| `*`      | beginner user      | search for a patient by name or identifier                     | quickly find an existing contact.                                               |
| `*`      | beginner user      | undo recent actions                                            | feel safe exploring the app.                                                    |
| `*`      | expert user        | organize patients using symptoms                                   | categorise patients based on clinical priority or special needs.                |
| `*`      | beginner user      | view what actions I have recently made                         | understand the current state of the database more accurately.                   |
| `*`      | expert user        | use command shortcuts or aliases when entering patient details | maximize my efficiency in typing during peak hours.                             |
| `*`      | expert user        | customise views or defaults                                    | the app matches my working habits.                                              |
| `*`      | user               | manually save my data at any point                             | be assured that I saved my data.                                                |

*Use this [link](https://docs.google.com/spreadsheets/d/1rFhnT22PNdGMJaEn7YMBqhdyV8b7M9sdSiOgtJ5c6J8/edit?gid=1121925862#gid=1121925862) for the most updated user stories.*

### Use cases

(For all use cases below, the **System** is the `ClinicConnect` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add a patient record**

**Preconditions**:
* The user has launched the app and is at the main window.

**Guarantees**:
* The patient will be added only if the user enters the format correctly, there is no duplicate patient record or there is no error with the save file.

**MSS**

1. The user requests to add a patient by providing their details.
2. ClinicConnect validates the input.
3. ClinicConnect adds the patient.
4. ClinicConnect saves the new data.
5. ClinicConnect displays a successful message.

   Use case ends.

**Extensions**

* 2a. ClinicConnect detects an error with the user’s input.
    * 2a1. ClinicConnect displays an unsuccessful message.

      Use case resumes from step 1.

* 2b. ClinicConnect detects a duplicate patient record.
    * 2b1. ClinicConnect displays an unsuccessful message.

      Use case resumes from step 1.

* 3a. ClinicConnect detects an error with the save file.
    * 3a1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since there is an issue with the save file, the use case should end here rather than retrying)


**Use case: UC2 - Delete a patient record**

**Preconditions**:
* The user has launched the app and is at the main window.

**Guarantees**:
* The patient will be deleted only if the user enters the format correctly, the list of patient records is not empty already or there is no error with the save file.

**MSS**

1. The user requests to delete a specific patient in the list.
2. ClinicConnect validates the input.
3. ClinicConnect deletes the patient.
4. ClinicConnect saves the new data.
5. ClinicConnect displays a successful message.

   Use case ends.

**Extensions**

* 2a. ClinicConnect detects an error with the user’s input.
    * 2a1. ClinicConnect displays an unsuccessful message.

      Use case resumes from step 1.

* 2b. ClinicConnect detects that the list is already empty.
    * 2b1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since the list is empty, the use case should end here rather than retrying)

* 3a. ClinicConnect detects an error with the save file.
    * 3a1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since there is an issue with the save file, the use case should end here rather than retrying)


**Use case: UC3 - Delete a group of selected patient records**

**Preconditions**:
* The user has launched the app and is at the main window.

**Guarantees**:
* The patients will be deleted only if the user enters the format correctly, the list of patient records is not empty already or there is no error with the save file.

**MSS**

1. The user requests to delete multiple specific patients in the list.
2. ClinicConnect validates the input.
3. ClinicConnect deletes the patients.
4. ClinicConnect saves the new data.
5. ClinicConnect displays a successful message.

   Use case ends.

**Extensions**

* 2a. ClinicConnect detects an error with the user’s input.
    * 2a1. ClinicConnect displays an unsuccessful message.

      Use case resumes from step 1.

* 2b. ClinicConnect detects that the list is already empty.
    * 2b1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since the list is empty, the use case should end here rather than retrying)

* 3a. ClinicConnect detects an error with the save file.
    * 3a1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since there is an issue with the save file, the use case should end here rather than retrying)


**Use case: UC4 - Delete a range of selected patient records**

**Preconditions**:
* The user has launched the app and is at the main window.

**Guarantees**:
* The patients will be deleted only if the user enters the format correctly, the list of patient records is not empty already or there is no error with the save file.

**MSS**

1. The user requests to delete a range of patients in the list.
2. ClinicConnect validates the input.
3. ClinicConnect deletes the patients.
4. ClinicConnect saves the new data.
5. ClinicConnect displays a successful message.

   Use case ends.

**Extensions**

* 2a. ClinicConnect detects an error with the user’s input.
    * 2a1. ClinicConnect displays an unsuccessful message.

      Use case resumes from step 1.

* 2b. ClinicConnect detects that the list is already empty.
    * 2b1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since the list is empty, the use case should end here rather than retrying)

* 3a. ClinicConnect detects an error with the save file.
    * 3a1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since there is an issue with the save file, the use case should end here rather than retrying)


**Use case: UC5 - Update a patient’s details**

**Preconditions**:
* The user has launched the app and is at the main window.

**Guarantees**:
* The patient will be updated only if the user enters the format correctly, the list of patient records is not empty already or there is no error with the save file.

**MSS**

1. The user requests to update specific details of a patient in the list.
2. ClinicConnect validates the input.
3. ClinicConnect updates the patient's details.
4. ClinicConnect saves the new data.
5. ClinicConnect displays a successful message.

   Use case ends.

**Extensions**

* 2a. ClinicConnect detects an error with the user’s input.
    * 2a1. ClinicConnect displays an unsuccessful message.

      Use case resumes from step 1.

* 2b. ClinicConnect detects that the list is empty.
    * 2b1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since the list is empty, the use case should end here rather than retrying)

* 3a. ClinicConnect detects an error with the save file.
    * 3a1. ClinicConnect displays an unsuccessful message.

      Use case ends. (Note: Since there is an issue with the save file, the use case should end here rather than retrying)


**Use case: UC6 - Search patient records**

**Preconditions**:
* User has launched the app and is at the main window.

**Guarantees**:
* A list of patients matching the criteria will be displayed if the user enters the search format correctly with at least one valid identifier.

**MSS**

1. The user requests to find patients using specific search criteria.
2. ClinicConnect validates the input format.
3. ClinicConnect filters the current list of patients based on the criteria.
4. ClinicConnect updates the UI to display the matching patient records.
5. ClinicConnect displays a message showing the number of patients found.

   Use case ends.

**Extensions**

* 2a. ClinicConnect detects an error with the user’s input (e.g., no fields provided).
    * 2a1. ClinicConnects display an unsuccessful message detailing the error.

      Use case resumes from step 1.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
