package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.common.View;

import java.util.Scanner;

/**
 * The `InputValidator` class provides methods to validate and retrieve integer, double and string inputs from the user.
 * It displays error messages when invalid input is detected, prompting the user to try again until valid input is received.
 * <p>
 * This class is useful in console-based applications to ensure that user inputs meet expected data types,
 * thus preventing runtime errors and improving the user experience by guiding them toward correct input formats.
 */
class InputValidator {
    private Scanner scanner;
    private View view;

    /**
     * Constructs an `InputValidator` with the specified Scanner and View.
     *
     * @param view    The View instance used to display messages and error prompts, allowing feedback to the user.
     */
    InputValidator(View view) {
        this.scanner = new Scanner(System.in);
        this.view = view;
    }

    /**
     * Continuously prompts the user for an integer input until a valid integer is entered.
     * If the input is not a valid integer, displays an error message and re-prompts.
     *
     * @param prompt The message displayed to the user to prompt for input.
     * @return A valid integer provided by the user.
     * @throws NumberFormatException if the user input cannot be parsed to an integer, which is handled within the loop.
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
     * Continuously prompts the user for a double input until a valid double is entered.
     * If the input is not a valid double, displays an error message and re-prompts.
     *
     * @param prompt The message displayed to the user to prompt for input.
     * @return A valid double provided by the user.
     * @throws NumberFormatException if the user input cannot be parsed to a double, which is handled within the loop.
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

    /**
     * Prompts the user for a string input and returns the entered value after trimming
     * any leading or trailing whitespace.
     * <p>
     * This method does not perform additional validation on the input, allowing the user
     * to enter any string, including empty or whitespace-only strings. It displays the specified
     * prompt message to guide the user.
     *
     * @param prompt The message displayed to the user to prompt for input.
     * @return The user-provided string, trimmed of leading and trailing whitespace.
     */
    String getStringInput(String prompt) {
        view.displayMessage(prompt);
        return scanner.nextLine().trim();
    }
}
