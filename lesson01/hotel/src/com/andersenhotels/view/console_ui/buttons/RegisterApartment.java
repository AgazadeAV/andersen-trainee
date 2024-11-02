package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * Represents the Register Apartment button in the console user interface.
 * This button allows for the registration of new apartments.
 */
public class RegisterApartment extends Button {
    /**
     * Constructs a Register Apartment button with the specified ConsoleUI.
     *
     * @param consoleUI the ConsoleUI associated with this button.
     */
    public RegisterApartment(ConsoleUI consoleUI) {
        super("Register Apartment", consoleUI);
    }

    /**
     * Executes the action associated with this button,
     * which registers a new apartment in the ConsoleUI.
     */
    @Override
    public void execute() {
        getConsoleUI().registerApartment();
    }
}
