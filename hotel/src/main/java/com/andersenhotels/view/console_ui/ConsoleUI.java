package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.MenuHandler;
import com.andersenhotels.view.common.View;

import java.util.List;

public class ConsoleUI implements View {
    private MenuHandler menuHandler;
    private Presenter presenter;
    private InputValidator inputValidator;
    private boolean isRunning;

    private static final String FILE_PATH = "src/main/resources/hotel_service_state.json";

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

    @Override
    public void startWork() {
        greetings();
        if (presenter.loadState(FILE_PATH)) {
            displayMessage("Application state loaded successfully.");
        } else {
            displayError("Error loading application state from " + FILE_PATH +
                    ". Starting with a new instance.");
        }
        selectItemFromMenu();
    }

    private void greetings() {
        displayMessage("Welcome to the Hotel Management Console Application!");
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
        int page = inputValidator.getIntInput("Enter page number from 1 to " + totalPages +
                " (integer value)\nPage size is 5:");
        return presenter.listApartments(page);
    }

    @Override
    public void finishWork() {
        if (presenter.saveState(FILE_PATH)) {
            displayMessage("Application state saved successfully.");
        } else {
            displayError("Error saving application state to " + FILE_PATH + ".");
        }
        this.isRunning = false;
        displayMessage("Good bye!");
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
