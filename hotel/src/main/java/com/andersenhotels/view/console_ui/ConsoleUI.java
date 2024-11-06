package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;

import java.util.List;
import java.util.Scanner;

/**
 * ConsoleUI provides a text-based user interface for interacting with the hotel management system.
 * It handles menu display, user input validation, and communicates with the Presenter to process actions.
 * This class implements the View interface, allowing it to receive commands and display results in the console.
 */
public class ConsoleUI implements View {
    private Scanner scanner;
    private MenuHandler menuHandler;
    private Presenter presenter;
    private InputValidator inputValidator;
    private boolean isRunning;

    /**
     * Constructs a new ConsoleUI instance with initialized scanner, menu handler, presenter, and input validator.
     * Sets the application to a running state.
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter(this);
        this.inputValidator = new InputValidator(scanner, this);
        this.isRunning = true;
    }

    /**
     * Starts the console application, displaying greetings and presenting the menu to the user.
     */
    @Override
    public void startWork() {
        greetings();
        selectItemFromMenu();
    }

    /**
     * Displays a welcome message at the beginning of the application.
     */
    private void greetings() {
        displayMessage("Welcome to the Hotel Management Console Application!");
    }

    /**
     * Main loop to display the menu, accept user choice, and execute selected option.
     * Continues until the user exits the application.
     *
     * @throws NumberFormatException if input is not a valid integer
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
     * Prompts the user to enter a price, validates it, and sends it to the Presenter to register a new apartment.
     */
    @Override
    public boolean registerApartment() {
        double price = inputValidator.getDoubleInput("Enter price for the apartment (double or integer value):");
        return presenter.registerApartment(price);
    }

    /**
     * Prompts the user to enter an apartment ID and guest name to make a reservation.
     * Validates inputs and passes them to the Presenter.
     */
    @Override
    public boolean reserveApartment() {
        int reserveId = inputValidator.getIntInput("Enter apartment ID to reserve (integer value):");
        System.out.println("Enter guest name: ");
        String clientName = scanner.nextLine().trim();
        return presenter.reserveApartment(reserveId, clientName);
    }

    /**
     * Prompts the user to enter an apartment ID to release an existing reservation.
     * Validates the input and passes it to the Presenter.
     */
    @Override
    public boolean releaseApartment() {
        int releaseId = inputValidator.getIntInput("Enter apartment ID to release (integer value):");
        return presenter.releaseApartment(releaseId);
    }

    /**
     * Prompts the user to enter page and page size values to view apartments with pagination.
     * Validates inputs and sends them to the Presenter.
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
     * Exits the console application by setting the running flag to false.
     * Displays a goodbye message to the user.
     */
    @Override
    public void finishWork() {
        this.isRunning = false;
        displayMessage("Good bye!");
    }

    /**
     * Displays a message to the console output.
     *
     * @param message The message to display.
     */
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the console error output.
     *
     * @param errorMessage The error message to display.
     */
    @Override
    public void displayError(String errorMessage) {
        System.err.println(errorMessage);
    }
}
