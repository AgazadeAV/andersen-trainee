package com.andersenhotels.view.console_ui;

import java.util.Scanner;

public class InputValidator {
    private Scanner scanner;

    public InputValidator(Scanner scanner) { // Изменен конструктор
        this.scanner = scanner;
    }

    public int getValidInteger(String prompt, int min, int max) {
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

    public double getValidDouble(String prompt) {
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

    public boolean isValidInteger(String input, int min, int max) {
        try {
            int integerValue = Integer.parseInt(input);
            return integerValue >= min && integerValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidDouble(String input) {
        try {
            double doubleValue = Double.parseDouble(input);
            return doubleValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
