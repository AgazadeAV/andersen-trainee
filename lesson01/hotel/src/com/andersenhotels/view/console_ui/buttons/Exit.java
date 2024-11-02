package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.ConsoleUI;

/**
 * Represents the Exit button in the console user interface.
 * This button terminates the application.
 */
public class Exit extends Button {
    /**
     * Constructs an Exit button with the specified ConsoleUI.
     *
     * @param consoleUI the ConsoleUI associated with this button.
     */
    public Exit(ConsoleUI consoleUI) {
        super("Exit", consoleUI);
    }

    /**
     * Executes the action associated with this button,
     * finishing the work of the ConsoleUI.
     */
    @Override
    public void execute() {
        getConsoleUI().finishWork();
    }
}
