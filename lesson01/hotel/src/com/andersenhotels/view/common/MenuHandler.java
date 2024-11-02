package com.andersenhotels.view.common;

import com.andersenhotels.view.common.buttons.*;

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
     * Initializes the MenuHandler with the available buttons for user actions.
     *
     * @param view The view instance to which the buttons will be linked.
     */
    public MenuHandler(View view) {
        buttons = new ArrayList<>();
        buttons.add(new RegisterApartment(view));
        buttons.add(new ReserveApartment(view));
        buttons.add(new ReleaseApartment(view));
        buttons.add(new ListApartments(view));
        buttons.add(new Exit(view));
    }

    /**
     * Retrieves a formatted string representing the menu options.
     *
     * @return A string containing the menu options.
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
