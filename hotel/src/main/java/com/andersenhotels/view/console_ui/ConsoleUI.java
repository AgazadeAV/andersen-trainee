package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleUI implements View {

    private final MenuHandler menuHandler;
    private final Presenter presenter;
    @Getter
    private final Scanner scanner = new Scanner(System.in);
    private boolean isRunning;

    public ConsoleUI(Presenter presenter) {
        this.presenter = presenter;
        this.menuHandler = new MenuHandler(this);
        this.isRunning = true;
    }

    @Override
    public void initialize() {
        greetings();
        selectItemFromMenu();
    }

    @Override
    public boolean registerApartment() {
        double price = ConsoleInputHandler.readDoubleInput("Enter price for the apartment (double or integer value):", this);
        return presenter.registerApartment(price);
    }

    @Override
    public boolean reserveApartment() {
        long reserveId = ConsoleInputHandler.readLongInput("Enter apartment ID to reserve (long value):", this);
        String guestName = ConsoleInputHandler.readStringInput("Enter guest name:", this);
        return presenter.reserveApartment(reserveId, guestName);
    }

    @Override
    public boolean releaseApartment() {
        long releaseId = ConsoleInputHandler.readLongInput("Enter reservation ID to release (long value):", this);
        return presenter.releaseApartment(releaseId);
    }

    @Override
    public List<String> listApartments() {
        try {
            int totalPages = presenter.getTotalPages();
            if (totalPages <= 0) {
                throw new ApartmentNotFoundException("No apartments registered. Nothing to show.");
            }
            int page = ConsoleInputHandler.readIntInput("Enter page number from 1 to " + totalPages + " (integer value):", this);
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
            int menuChoice = ConsoleInputHandler.readIntInput("Please select an option from the menu:", this);
            try {
                menuHandler.execute(menuChoice);
            } catch (WrongMenuChoiceException e) {
                displayError(e.getMessage());
            }
        }
    }
}
