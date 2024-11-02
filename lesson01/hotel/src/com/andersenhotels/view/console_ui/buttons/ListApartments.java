package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * Represents the List Apartments button in the console user interface.
 * This button displays the list of available apartments.
 */
public class ListApartments extends Button {
    /**
     * Constructs a List Apartments button with the specified ConsoleUI.
     *
     * @param consoleUI the ConsoleUI associated with this button.
     */
    public ListApartments(ConsoleUI consoleUI) {
        super("List Apartments", consoleUI);
    }

    /**
     * Executes the action associated with this button,
     * which lists the available apartments in the ConsoleUI.
     */
    @Override
    public void execute() {
        getConsoleUI().listApartments();
    }
}
