package com.andersenhotels.view.console_ui;

import com.andersenhotels.presenter.Presenter;
import com.andersenhotels.view.View;

import java.util.Scanner;

/**
 * ConsoleUI is the user interface for the Hotel Management application.
 * It interacts with the user via the console, handling input and
 * displaying relevant information related to hotel management tasks.
 */
public class ConsoleUI implements View {
    private static final int MIN_VALID_VALUE = 1;
    private Scanner scanner;
    private MenuHandler menuHandler;
    private Presenter presenter;
    private InputValidator inputValidator;
    private boolean work;

    /**
     * Constructs a new ConsoleUI instance, initializing the scanner,
     * menu handler, presenter, input validator, and work status.
     */
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter();
        this.inputValidator = new InputValidator(scanner); // Passes scanner for input validation
        this.work = true;
    }

    /**
     * Starts the console user interface, displaying a welcome message
     * and presenting the menu to the user for interaction.
     */
    @Override
    public void startWork() {
        greetings();
        selectItemFromMenu();
    }

    /**
     * Displays a greeting message to the user.
     */
    private void greetings() {
        System.out.println("Welcome to the Hotel Management Console Application!");
    }

    /**
     * Continuously displays the menu and processes user selections until
     * the user decides to exit the application.
     */
    private void selectItemFromMenu() {
        while (work) {
            System.out.println(menuHandler.getMenu());
            String menuChoiceStr = scanner.nextLine().trim();
            if (inputValidator.isValidInteger(menuChoiceStr, MIN_VALID_VALUE, menuHandler.getMenuSize())) {
                int choice = Integer.parseInt(menuChoiceStr);
                menuHandler.execute(choice);
            } else {
                System.out.println("Invalid menu option entered.\n" +
                        "Please enter a valid number from the menu: from 1 to " + menuHandler.getMenuSize());
            }
        }
    }

    /**
     * Prompts the user to register a new apartment with a specified price.
     */
    public void registerApartment() {
        double price = inputValidator.getValidDouble("Enter price for the apartment (double or integer value): ");
        presenter.registerApartment(price);
    }

    /**
     * Prompts the user to reserve an apartment by providing the apartment ID
     * and client name.
     */
    public void reserveApartment() {
        int apartmentsCount = presenter.getApartmentsCount();
        int reserveId = inputValidator.getValidInteger("Enter apartment ID to reserve (integer value): ",
                MIN_VALID_VALUE, apartmentsCount);
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        presenter.reserveApartment(reserveId, clientName);
    }

    /**
     * Prompts the user to release an apartment from reservation by providing the apartment ID.
     */
    public void releaseApartment() {
        int apartmentsCount = presenter.getApartmentsCount();
        int releaseId = inputValidator.getValidInteger("Enter apartment ID to release (integer value): ",
                MIN_VALID_VALUE, apartmentsCount);
        presenter.releaseApartment(releaseId);
    }

    /**
     * Prompts the user for pagination information and lists apartments based on
     * the provided page number and size.
     */
    public void listApartments() {
        int page = inputValidator.getValidInteger("Enter page number (integer value): ",
                MIN_VALID_VALUE, Integer.MAX_VALUE);
        int pageSize = inputValidator.getValidInteger("Enter page size (integer value): ",
                MIN_VALID_VALUE, Integer.MAX_VALUE);
        presenter.listApartments(page, pageSize);
    }

    /**
     * Ends the operation of the console user interface, displaying a farewell message.
     */
    @Override
    public void finishWork() {
        this.work = false;
        System.out.println("Good bye!");
    }
}
