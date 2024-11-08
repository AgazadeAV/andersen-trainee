package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;

import java.util.List;

/**
 * ConsoleUI provides a text-based user interface for interacting with the hotel management system.
 * It handles displaying menus, validating user input, and communicating with the `Presenter` to process actions.
 * <p>
 * This class implements the `View` interface, allowing it to receive commands from the user
 * and display results or errors in the console. It serves as a bridge between user input and the core
 * functionality managed by `Presenter`.
 */
public class ConsoleUI implements View {
    private MenuHandler menuHandler;
    private Presenter presenter;
    private InputValidator inputValidator;
    private boolean isRunning;

    /**
     * Constructs a new ConsoleUI instance with an initialized menu handler,
     * presenter, and input validator. Sets the application to a running state.
     */
    public ConsoleUI() {
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter(this);
        this.inputValidator = new InputValidator(this);
        this.isRunning = true;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setInputValidator(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    /**
     * Starts the console application, displaying a greeting message and presenting the menu
     * to the user, allowing them to begin interacting with the application.
     */
    @Override
    public void startWork() {
        greetings();
        selectItemFromMenu();
    }

    /**
     * Displays a welcome message when the application begins.
     */
    private void greetings() {
        displayMessage("Welcome to the Hotel Management Console Application!");
    }

    /**
     * Main loop to display the menu, accept user choice, and execute the selected option.
     * Continues until the user chooses to exit the application.
     *
     * @throws NumberFormatException if the input is not a valid integer.
     */
    private void selectItemFromMenu() {
        while (isRunning) {
            try {
                displayMessage(menuHandler.getMenu());
                int menuChoice = inputValidator.getIntInput("Please select an option from menu:");
                try {
                    menuHandler.execute(menuChoice);
                } catch (WrongMenuChoiceException e) {
                    displayError(e.getMessage());
                }
            } catch (NumberFormatException e) {
                displayError("Invalid input. Please enter a valid integer from the menu: from 1 to " +
                        menuHandler.getMenuSize() + ".");
            }
        }
    }

    /**
     * Prompts the user to enter a price, validates it, and sends it to the `Presenter` to register a new apartment.
     * Displays success or error messages based on the result of the operation.
     *
     * @return `true` if the apartment was registered successfully, `false` otherwise.
     */
    @Override
    public boolean registerApartment() {
        double price = inputValidator.getDoubleInput("Enter price for the apartment (double or integer value):");
        return presenter.registerApartment(price);
    }

    /**
     * Prompts the user to enter an apartment ID and guest name to make a reservation.
     * Validates inputs and passes them to the `Presenter` for processing.
     *
     * @return `true` if the reservation was successfully made, `false` otherwise.
     */
    @Override
    public boolean reserveApartment() {
        int reserveId = inputValidator.getIntInput("Enter apartment ID to reserve (integer value):");
        String guestName = inputValidator.getStringInput("Enter guest name: ");
        return presenter.reserveApartment(reserveId, guestName);
    }

    /**
     * Prompts the user to enter an apartment ID to release an existing reservation.
     * Validates the input and passes it to the `Presenter` for processing.
     *
     * @return `true` if the reservation was successfully released, `false` otherwise.
     */
    @Override
    public boolean releaseApartment() {
        int releaseId = inputValidator.getIntInput("Enter apartment ID to release (integer value):");
        return presenter.releaseApartment(releaseId);
    }

    /**
     * Prompts the user to enter page details to view apartments with pagination.
     * Retrieves apartment details for the specified page and displays them in a formatted list.
     *
     * @return A list of formatted strings containing apartment details for the specified page.
     * @throws ApartmentNotFoundException if no apartments are found for the selected page.
     */
    @Override
    public List<String> listApartments() {
        int totalPages = presenter.getTotalPages();
        if (totalPages <= 0) {
            throw new ApartmentNotFoundException("No apartments registered. Nothing to show.");
        }
        int page = inputValidator.getIntInput("Enter page number from 1 to " + totalPages +
                " (integer value)\nPage size is 5:");
        return presenter.listApartments(page);
    }

    /**
     * Exits the console application by setting the running flag to `false`.
     * Displays a farewell message before closing the application.
     */
    @Override
    public void finishWork() {
        this.isRunning = false;
        displayMessage("Good bye!");
    }

    /**
     * Displays a general message to the console output.
     *
     * @param message The message to display to the user.
     */
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the console error output.
     *
     * @param errorMessage The error message to display to the user.
     */
    @Override
    public void displayError(String errorMessage) {
        System.err.println(errorMessage);
    }
}
