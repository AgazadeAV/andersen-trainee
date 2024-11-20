package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ConsoleUI implements View {

    private MenuHandler menuHandler;
    private Presenter presenter;
    private InputValidator inputValidator;
    private boolean isRunning;
    private boolean isTesting;

    public ConsoleUI() {
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter(this);
        this.inputValidator = new InputValidator(this);
        this.isRunning = true;
    }

    public static void main(String[] args) {
        View view = new ConsoleUI();
        view.initialize();
    }

    @Override
    public void initialize() {
        selectStorageType();
        greetings();
        if (loadState()) {
            displayMessage("Application state loaded successfully.");
        } else {
            displayError("Error loading application state. Starting with a new instance.");
        }
        selectItemFromMenu();
    }

    public void selectStorageType() {
        System.out.println("Choose storage type:");
        System.out.println("1. JSON");
        System.out.println("2. Database");

        int choice = -1;
        while (choice < 1 || choice > 2) {
            try {
                choice = inputValidator.getIntInput("Enter your choice (1 or 2): ");

                if (choice == 1) {
                    presenter.setStorageType(1);
                    System.out.println("Selected JSON storage.");
                } else if (choice == 2) {
                    presenter.setStorageType(2);
                    System.out.println("Selected Database storage.");
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
            }
        }
    }

    private void greetings() {
        displayMessage("Welcome to the Hotel Management Console Application!");
    }

    private boolean loadState() {
        return isTesting ? presenter.loadStateForTests() : presenter.loadState();
    }

    private void selectItemFromMenu() {
        while (isRunning) {
            displayMessage(menuHandler.getMenu());

            int menuChoice = inputValidator.getIntInput("Please select an option from the menu:");
            try {
                menuHandler.execute(menuChoice);
            } catch (WrongMenuChoiceException e) {
                displayError(e.getMessage());
            } catch (NumberFormatException e) {
                displayError("Invalid input. Please enter a valid integer from the menu: from 1 to " +
                        menuHandler.getMenuSize() + ".");
            }
        }
    }

    @Override
    public boolean registerApartment() {
        double price = inputValidator.getDoubleInput("Enter price for the apartment (double or integer value):");
        return presenter.registerApartment(price);
    }

    @Override
    public boolean reserveApartment() {
        int reserveId = inputValidator.getIntInput("Enter apartment ID to reserve (integer value):");
        String guestName = inputValidator.getStringInput("Enter guest name: ");
        return presenter.reserveApartment(reserveId, guestName);
    }

    @Override
    public boolean releaseApartment() {
        int releaseId = inputValidator.getIntInput("Enter apartment ID to release (integer value):");
        return presenter.releaseApartment(releaseId);
    }

    @Override
    public List<String> listApartments() {
        int totalPages = presenter.getTotalPages();
        if (totalPages <= 0) {
            throw new ApartmentNotFoundException("No apartments registered. Nothing to show.");
        }
        int page = inputValidator.getIntInput("Enter page number from 1 to " + totalPages + " (integer value)\n" +
                "Page size is 5:");
        return presenter.listApartments(page);
    }

    @Override
    public void complete() {
        if (saveState()) {
            displayMessage("Application state saved successfully.");
        } else {
            displayError("Error saving application state.");
        }
        this.isRunning = false;
        displayMessage("Good bye!");
    }

    private boolean saveState() {
        return isTesting ? presenter.saveStateForTests() : presenter.saveState();
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayError(String errorMessage) {
        System.err.println(errorMessage);
    }
}
