package view.console_ui.buttons;

import view.console_ui.ConsoleUI;

public abstract class Button {
    private final String description;
    private ConsoleUI consoleUI;

    public Button(String description, ConsoleUI consoleUI) {
        this.description = description;
        this.consoleUI = consoleUI;
    }

    ConsoleUI getConsoleUI() {
        return consoleUI;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute();
}
