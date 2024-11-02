package com.andersenhotels.view.console_ui;

import java.util.Scanner;

/**
 * The InputValidator class is responsible for validating user input within the console user interface.
 * It ensures that the input meets specified criteria before it is processed further in the application.
 */
public class InputValidator {
    private Scanner scanner;

    /**
     * Constructs an InputValidator with the provided Scanner instance.
     *
     * @param scanner the Scanner used for reading user input from the console
     */
    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prompts the user for input and returns a valid integer within the specified range.
     *
     * @param prompt the message displayed to the user for input
     * @param min the minimum valid integer value
     * @param max the maximum valid integer value
     * @return a valid integer input from the user
     */
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

    /**
     * Prompts the user for input and returns a valid double value.
     *
     * @param prompt the message displayed to the user for input
     * @return a valid double input from the user
     */
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

    /**
     * Validates if the given input can be parsed as an integer and falls within the specified range.
     *
     * @param input the input string to validate
     * @param min the minimum valid integer value
     * @param max the maximum valid integer value
     * @return true if the input is a valid integer within the range, false otherwise
     */
    public boolean isValidInteger(String input, int min, int max) {
        try {
            int integerValue = Integer.parseInt(input);
            return integerValue >= min && integerValue <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the given input can be parsed as a valid double value.
     *
     * @param input the input string to validate
     * @return true if the input is a valid non-negative double, false otherwise
     */
    public boolean isValidDouble(String input) {
        try {
            double doubleValue = Double.parseDouble(input);
            return doubleValue >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
