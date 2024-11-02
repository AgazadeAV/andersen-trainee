package view.console_ui;

import presenter.Presenter;
import view.View;

import java.util.Scanner;

public class ConsoleUI implements View {
    private MenuHandler menuHandler;
    private Presenter presenter;
    private Scanner scanner;
    private boolean work;

    public ConsoleUI() {
        this.menuHandler = new MenuHandler(this);
        this.presenter = new Presenter();
        this.scanner = new Scanner(System.in);
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
            if (isValidInteger(menuChoiceStr, 1, menuHandler.getMenuSize())) {
                int choice = Integer.parseInt(menuChoiceStr);
                menuHandler.execute(choice);
            } else {
                System.out.println("Invalid menu option entered.\n" +
                        "Please enter a valid number from the menu: from 1 to " + menuHandler.getMenuSize());
            }
        }
    }

    public void registerApartment() {
        double price = getValidDouble("Enter price for the apartment (double or integer value): ");
        presenter.registerApartment(price);
    }

    public void reserveApartment() {
        int apartmentsCount = presenter.getApartmentsCount();
        int reserveId = getValidInteger("Enter apartment ID to reserve (integer value): ", 1, apartmentsCount);
        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        presenter.reserveApartment(reserveId, clientName);
    }

    public void releaseApartment() {
        int apartmentsCount = presenter.getApartmentsCount();
        int releaseId = getValidInteger("Enter apartment ID to release (integer value): ", 1, apartmentsCount);
        presenter.releaseApartment(releaseId);
    }

    public void listApartments() {
        int page = getValidInteger("Enter page number (integer value): ", 1, Integer.MAX_VALUE);
        int pageSize = getValidInteger("Enter page size (integer value): ", 1, Integer.MAX_VALUE);
        presenter.listApartments(page, pageSize);
    }

    private int getValidInteger(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (isValidInteger(input, min, max)) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Invalid input, please enter a valid integer between " + min + " and " + max + ".");
            }
        }
    }

    private double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (isValidDouble(input)) {
                return Double.parseDouble(input);
            } else {
                System.out.println("Invalid price, please enter a valid price.");
            }
        }
    }

    private boolean isValidInteger(String input, int min, int max) {
        try {
            int integerValue = Integer.parseInt(input);
            return integerValue >= min && integerValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidDouble(String input) {
        try {
            double doubleValue = Double.parseDouble(input);
            return doubleValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void finishWork() {
        this.work = false;
        System.out.println("Good bye!");
    }
}
