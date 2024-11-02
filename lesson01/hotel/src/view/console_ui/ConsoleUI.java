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
        this.presenter = new Presenter(this);
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
        boolean flag = true;
        while (flag) {
            System.out.print("Enter price for the apartment (example 25.10): ");
            String priceStr = scanner.nextLine().trim();
            if (isValidDouble(priceStr)) {
                double price = Double.parseDouble(priceStr);
                presenter.registerApartment(price);
                flag = false;
            } else {
                System.out.println("Invalid price, please enter valid price.");
            }
        }
    }

    public void reserveApartment() {
        boolean flag = true;
        int apartmentsCount = presenter.getApartmentsCount();
        while (flag) {
            System.out.print("Enter apartment ID to reserve: ");
            String reserveIdStr = scanner.nextLine().trim();
            if (isValidInteger(reserveIdStr, 1, apartmentsCount)) {
                int reserveId = Integer.parseInt(reserveIdStr);
                System.out.print("Enter client name: ");
                String clientName = scanner.nextLine();
                presenter.reserveApartment(reserveId, clientName);
                flag = false;
            } else {
                System.out.println("Invalid apartment id choice.\n" +
                        "Please enter a valid apartment id: from 1 to " + apartmentsCount);
            }
        }
    }

    public void releaseApartment() {
        boolean flag = true;
        int apartmentsCount = presenter.getApartmentsCount();
        while (flag) {
            System.out.print("Enter apartment ID to release: ");
            String releaseIdStr = scanner.nextLine().trim();
            if (isValidInteger(releaseIdStr, 1, apartmentsCount)) {
                int releaseId = Integer.parseInt(releaseIdStr);
                System.out.print("Enter client name: ");
                presenter.releaseApartment(releaseId);
                flag = false;
            } else {
                System.out.println("Invalid apartment id choice.\n" +
                        "Please enter a valid apartment id: from 1 to " + apartmentsCount);
            }
        }
    }

    public void listApartments() {
        boolean flag = true;
        int page = 0;
        int pageSize = 0;
        while (flag) {
            System.out.print("Enter page number: ");
            String pageStr = scanner.nextLine();
            try {
                page = Integer.parseInt(pageStr);
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid page choice. Please enter integer.");
            }
        }
        flag = true;
        while (flag) {
            System.out.print("Enter page size: ");
            String pageSizeStr = scanner.nextLine();
            try {
                pageSize = Integer.parseInt(pageSizeStr);
                flag = false;
            } catch (NumberFormatException e) {
                System.out.println("Invalid page size choice. Please enter integer.");
            }
        }
        presenter.listApartments(page, pageSize);

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
            return doubleValue >= 0 && input.contains(".");
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
