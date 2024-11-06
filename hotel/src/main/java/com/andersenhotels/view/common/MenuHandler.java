package com.andersenhotels.view.common;

import com.andersenhotels.view.common.buttons.*;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * The `MenuHandler` class manages the display and execution of a menu within the console UI.
 * It initializes a list of `Button` objects, each representing a specific action the user can perform
 * in the application, such as registering apartments, making reservations, or exiting.
 * <p>
 * The `MenuHandler` class allows for dynamic menu generation and provides functionality to execute
 * user-selected actions, maintaining a clean separation between menu presentation and logic execution.
 */
public class MenuHandler {
    private List<Button> buttons;

    /**
     * Constructs a `MenuHandler` instance with predefined menu actions.
     * Initializes buttons for key actions including registering apartments, reserving apartments,
     * releasing apartments, listing apartments, and exiting the application.
     *
     * @param view The `View` instance that each button interacts with, allowing actions to
     *             communicate with the user interface.
     */
    public MenuHandler(View view) {
        buttons = new ArrayList<>();
        buttons.add(new RegisterApartment(view));
        buttons.add(new ReserveApartment(view));
        buttons.add(new ReleaseApartment(view));
        buttons.add(new ListApartments(view));
        buttons.add(new Exit(view));
    }

    public List<Button> getButtons() {
        return buttons;
    }

    /**
     * Generates and returns a formatted menu string.
     * This string lists all available options, each with an associated number, allowing the user
     * to select an option by entering the corresponding number.
     *
     * @return A formatted menu string displaying each menu option, numbered sequentially.
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
     * Validates that the user's choice is within the valid range of menu options, and if valid,
     * invokes the `execute` method of the corresponding button.
     *
     * @param choice The menu option number selected by the user, expected to be an integer between
     *               1 and the total number of menu options.
     * @throws WrongMenuChoiceException if the choice is invalid, i.e., outside the range of available options.
     *                                  The exception message guides the user to select a valid option.
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
     * Retrieves the total number of menu options available.
     * Useful for input validation and dynamically displaying menu information.
     *
     * @return The number of buttons in the menu, representing the available actions.
     */
    public int getMenuSize() {
        return buttons.size();
    }
}
