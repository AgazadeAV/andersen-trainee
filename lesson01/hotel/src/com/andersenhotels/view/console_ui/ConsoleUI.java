package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.view.View;

import java.util.Scanner;

public class ConsoleUI implements View {
    private Scanner scanner;
    private MenuHandler menuHandler;
    private Presenter presenter;
    private InputValidator inputValidator;
    private boolean work;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter();
        this.inputValidator = new InputValidator(scanner); // Передача сканера
        this.work = true;
    }

    @Override
    public void startWork() {
        greetings();
        selectItemFromMenu();
    }

    private void greetings() {
        System.out.println("Welcome to the Hotel Management Console Application!");
    }

    private void selectItemFromMenu() {
        while (work) {
            System.out.println(menuHandler.getMenu());
            String menuChoiceStr = scanner.nextLine().trim();
            if (inputValidator.isValidInteger(menuChoiceStr, 1, menuHandler.getMenuSize())) {
                int choice = Integer.parseInt(menuChoiceStr);
                menuHandler.execute(choice);
            } else {
                System.out.println("Invalid menu option entered.\n" +
                        "Please enter a valid number from the menu: from 1 to " + menuHandler.getMenuSize());
            }
        }
    }

    public void registerApartment() {
        double price = inputValidator.getValidDouble("Enter price for the apartment (double or integer value): ");
        presenter.registerApartment(price);
    }

    public void reserveApartment() {
        int apartmentsCount = presenter.getApartmentsCount();
        int reserveId = inputValidator.getValidInteger("Enter apartment ID to reserve (integer value): ", 1, apartmentsCount);
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        presenter.reserveApartment(reserveId, clientName);
    }

    public void releaseApartment() {
        int apartmentsCount = presenter.getApartmentsCount();
        int releaseId = inputValidator.getValidInteger("Enter apartment ID to release (integer value): ", 1, apartmentsCount);
        presenter.releaseApartment(releaseId);
    }

    public void listApartments() {
        int page = inputValidator.getValidInteger("Enter page number (integer value): ", 1, Integer.MAX_VALUE);
        int pageSize = inputValidator.getValidInteger("Enter page size (integer value): ", 1, Integer.MAX_VALUE);
        presenter.listApartments(page, pageSize);
    }

    @Override
    public void finishWork() {
        this.work = false;
        System.out.println("Good bye!");
    }
}
