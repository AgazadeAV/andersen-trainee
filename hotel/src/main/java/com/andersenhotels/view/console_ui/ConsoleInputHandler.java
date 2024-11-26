package com.andersenhotels.view.console_ui;

public class ConsoleInputHandler {

    public static int readIntInput(String prompt, ConsoleUI view) {
        while (true) {
            try {
                view.displayMessage(prompt);
                return Integer.parseInt(view.getScanner().nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid integer value. Please try again.");
            }
        }
    }

    public static long readLongInput(String prompt, ConsoleUI view) {
        while (true) {
            try {
                view.displayMessage(prompt);
                return Long.parseLong(view.getScanner().nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid long value. Please try again.");
            }
        }
    }

    public static double readDoubleInput(String prompt, ConsoleUI view) {
        while (true) {
            try {
                view.displayMessage(prompt);
                return Double.parseDouble(view.getScanner().nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid number. Please enter a valid double or integer value.");
            }
        }
    }

    public static String readStringInput(String prompt, ConsoleUI view) {
        while (true) {
            view.displayMessage(prompt);
            String input = view.getScanner().nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                view.displayError("Input cannot be empty. Please try again.");
            }
        }
    }
}
