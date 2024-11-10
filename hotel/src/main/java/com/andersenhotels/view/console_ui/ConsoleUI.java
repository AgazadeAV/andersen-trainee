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

    @Getter
    private static final String TEST_PATH = "src/main/resources/hotel_service_state_test.json";

    public ConsoleUI() {
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter(this);
        this.inputValidator = new InputValidator(this);
        this.isRunning = true;
    }

    @Override
    public void startWork() {
        greetings();
        if (loadState()) {
            displayMessage("Application state loaded successfully.");
        } else {
            displayError("Error loading application state. Starting with a new instance.");
        }
        selectItemFromMenu();
    }

    private void greetings() {
        displayMessage("Welcome to the Hotel Management Console Application!");
    }

    private boolean loadState() {
        return isTesting ? presenter.loadStateForTests(TEST_PATH) : presenter.loadState();
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
    public void finishWork() {
        if (saveState()) {
            displayMessage("Application state saved successfully.");
        } else {
            displayError("Error saving application state.");
        }
        this.isRunning = false;
        displayMessage("Good bye!");
    }

    private boolean saveState() {
        return isTesting ? presenter.saveStateForTests(TEST_PATH) : presenter.saveState();
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
