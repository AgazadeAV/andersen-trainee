package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * Abstract class representing a button in the console user interface.
 * Each button has a description and a reference to the ConsoleUI.
 */
public abstract class Button {
    private final String description;
    private ConsoleUI consoleUI;

    /**
     * Constructs a new Button instance with a description and ConsoleUI reference.
     *
     * @param description the description of the button.
     * @param consoleUI the ConsoleUI associated with this button.
     */
    public Button(String description, ConsoleUI consoleUI) {
        this.description = description;
        this.consoleUI = consoleUI;
    }

    /**
     * Gets the ConsoleUI associated with this button.
     *
     * @return the ConsoleUI instance.
     */
    protected ConsoleUI getConsoleUI() {
        return consoleUI;
    }

    /**
     * Gets the description of the button.
     *
     * @return the description of the button.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Abstract method to execute the button's action.
     * This method should be implemented by subclasses.
     */
    public abstract void execute();
}
