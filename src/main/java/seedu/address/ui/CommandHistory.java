package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private final List<String> commandHistory = new ArrayList<>();
    private int historyIndex = 0;
    private String currentInput = "";

    public CommandHistory() {

    }

    public void addCommand(String command) {
        assert command != null;
        commandHistory.add(command);
        historyIndex = commandHistory.size();
        currentInput="";

    }

    public String getPreviousCommand(String currentInput) {
        assert currentInput != null;
        if (commandHistory.isEmpty()) {
            return currentInput;
        }

        // set current input
        if (historyIndex == commandHistory.size()) {
            this.currentInput = currentInput;
        }

        if (historyIndex > 0) {
            historyIndex -= 1;
        }

        return commandHistory.get(historyIndex);
    }

    public String getNextCommand() {
        if (commandHistory.isEmpty()) {
            return currentInput;
        }

        if (historyIndex < commandHistory.size()) {
            historyIndex += 1;
        }

        if (historyIndex == commandHistory.size()) {
            return currentInput;
        } else {
            return commandHistory.get(historyIndex);
        }
    }

}
