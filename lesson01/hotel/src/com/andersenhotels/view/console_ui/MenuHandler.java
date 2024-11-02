package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.console_ui.buttons.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The MenuHandler class manages the menu options in the console user interface for the hotel management application.
 * It contains a list of buttons representing different actions the user can take and handles user input to execute
 * the corresponding actions.
 */
public class MenuHandler {
    private List<Button> buttons;

    /**
     * Constructs a MenuHandler and initializes the available menu options.
     *
     * @param consoleUI the ConsoleUI instance that the buttons will interact with
     */
    public MenuHandler(ConsoleUI consoleUI) {
        buttons = new ArrayList<>();
        buttons.add(new RegisterApartment(consoleUI));
        buttons.add(new ReserveApartment(consoleUI));
        buttons.add(new ReleaseApartment(consoleUI));
        buttons.add(new ListApartments(consoleUI));
        buttons.add(new Exit(consoleUI));
    }

    /**
     * Constructs and returns the menu string to be displayed to the user.
     *
     * @return a string representation of the menu options
     */
    public String getMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Please select an option from the menu.\nMenu:\n");
        for (int i = 0; i < buttons.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(buttons.get(i).getDescription())
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * Executes the action corresponding to the user's menu choice.
     *
     * @param choice the user's menu choice (1-based index)
     */
    public void execute(int choice) {
        Button button = buttons.get(choice - 1);
        button.execute();
    }

    /**
     * Returns the number of menu options available.
     *
     * @return the size of the menu (number of buttons)
     */
    public int getMenuSize() {
        return buttons.size();
    }
}
