package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.common.View;

import java.util.Scanner;

/**
 * The `InputValidator` class provides methods to validate and retrieve integer and double inputs from the user.
 * It displays error messages and prompts the user to retry if invalid input is provided.
 */
class InputValidator {
    private Scanner scanner;
    private View view;

    /**
     * Constructs an `InputValidator` with the specified Scanner and View.
     *
     * @param scanner The Scanner to read input from.
     * @param view    The View used to display messages and errors.
     */
    InputValidator(Scanner scanner, View view) {
        this.scanner = scanner;
        this.view = view;
    }

    /**
     * Prompts the user for an integer input, displays an error if the input is invalid,
     * and keeps prompting until a valid integer is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return A valid integer input from the user.
     */
    int getIntInput(String prompt) {
        while (true) {
            view.displayMessage(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid integer value. Please try again.");
            }
        }
    }

    /**
     * Prompts the user for a double input, displays an error if the input is invalid,
     * and keeps prompting until a valid double is entered.
     *
     * @param prompt The prompt message to display to the user.
     * @return A valid double input from the user.
     */
    double getDoubleInput(String prompt) {
        while (true) {
            view.displayMessage(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid number. Please enter a valid double or integer value.");
            }
        }
    }
}
