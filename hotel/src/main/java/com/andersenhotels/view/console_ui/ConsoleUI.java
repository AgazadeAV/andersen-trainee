package com.andersenhotels.view.console_ui;

import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.View;
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

    public ConsoleUI() {
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter(this);
        this.inputValidator = new InputValidator(this);
        this.isRunning = true;
    }

    @Override
    public void initialize() {
        greetings();
        selectItemFromMenu();
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
        try {
            int totalPages = presenter.getTotalPages();
            if (totalPages <= 0) {
                throw new ApartmentNotFoundException("No apartments registered. Nothing to show.");
            }
            int page = inputValidator.getIntInput("Enter page number from 1 to " + totalPages +
                    " (integer value)\n" + "Page size is " + ConfigManager.getPageSizeForPagination() + ":");
            return presenter.listApartments(page);
        } catch (ApartmentNotFoundException e) {
            displayError(e.getMessage());
            return List.of();
        }
    }

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayError(String errorMessage) {
        System.err.println(errorMessage);
    }

    @Override
    public void complete() {
        displayMessage("Good bye!");
        this.isRunning = false;
    }

    private void greetings() {
        displayMessage("Welcome to the Hotel Management Console Application!");
        displayHotelInfo();
    }

    private void displayHotelInfo() {
        if (presenter.getHotel() != null) {
            displayMessage("Current hotel ID: " + presenter.getHotel().getId());
        } else {
            displayMessage("No hotel found. A new hotel will be created.");
        }
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
}
