package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * Represents the Reserve Apartment button in the console user interface.
 * This button allows for the reservation of an apartment.
 */
public class ReserveApartment extends Button {
    /**
     * Constructs a Reserve Apartment button with the specified ConsoleUI.
     *
     * @param consoleUI the ConsoleUI associated with this button.
     */
    public ReserveApartment(ConsoleUI consoleUI) {
        super("Reserve Apartment", consoleUI);
    }

    /**
     * Executes the action associated with this button,
     * which reserves an apartment in the ConsoleUI.
     */
    @Override
    public void execute() {
        getConsoleUI().reserveApartment();
    }
}
