package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * Represents the Release Apartment button in the console user interface.
 * This button allows for the release of an apartment from reservation.
 */
public class ReleaseApartment extends Button {
    /**
     * Constructs a Release Apartment button with the specified ConsoleUI.
     *
     * @param consoleUI the ConsoleUI associated with this button.
     */
    public ReleaseApartment(ConsoleUI consoleUI) {
        super("Release Apartment", consoleUI);
    }

    /**
     * Executes the action associated with this button,
     * which releases an apartment in the ConsoleUI.
     */
    @Override
    public void execute() {
        getConsoleUI().releaseApartment();
    }
}
