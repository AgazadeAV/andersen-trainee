package com.andersenhotels.view.common;

import com.andersenhotels.view.common.buttons.*;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * The `MenuHandler` class manages the display and execution of a menu in the console UI.
 * It initializes a list of `Button` objects, each representing an action in the application,
 * and allows the user to execute a selected action.
 */
public class MenuHandler {
    private List<Button> buttons;

    /**
     * Constructs a `MenuHandler` with predefined menu actions.
     * Initializes buttons for actions such as registering apartments, reserving apartments, releasing apartments,
     * listing apartments, and exiting the application.
     *
     * @param view The `View` instance that each button will interact with.
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
     * Generates and returns a formatted menu as a string.
     * The menu lists all available options, each associated with a number.
     *
     * @return A formatted menu string displaying each menu option.
     */
    public String getMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu:\n");
        for (int i = 0; i < buttons.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(buttons.get(i).getDescription())
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * Executes the action associated with the selected menu option.
     * Validates the user's choice to ensure it falls within the valid range of menu options.
     *
     * @param choice The menu option number selected by the user.
     * @throws WrongMenuChoiceException if the choice is invalid, i.e., outside the range of available options.
     */
    public void execute(int choice) {
        if (choice >= 1 && choice <= getMenuSize()) {
            Button button = buttons.get(choice - 1);
            button.execute();
        } else {
            throw new WrongMenuChoiceException("Invalid menu option entered. " +
                    "Please enter a valid number from the menu: from 1 to " + getMenuSize() + ".");
        }
    }

    /**
     * Gets the total number of menu options.
     *
     * @return The number of buttons in the menu.
     */
    public int getMenuSize() {
        return buttons.size();
    }
}
