package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.common.View;

import java.util.Scanner;
import java.util.function.Function;

public class InputValidator {

    private final Scanner scanner;
    private final View view;

    public InputValidator(View view) {
        this.scanner = new Scanner(System.in);
        this.view = view;
    }

    public InputValidator(View view, Scanner scanner) {
        this.scanner = scanner;
        this.view = view;
    }

    public int getIntInput(String prompt) {
        return getInput(prompt, "Invalid integer value. Please try again.", Integer::parseInt);
    }

    public double getDoubleInput(String prompt) {
        return getInput(prompt, "Invalid number. Please enter a valid double or integer value.", Double::parseDouble);
    }

    private <T> T getInput(String prompt, String errorMessage, Function<String, T> parser) {
        while (true) {
            view.displayMessage(prompt);
            try {
                return parser.apply(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError(errorMessage);
            }
        }
    }

    public String getStringInput(String prompt) {
        view.displayMessage(prompt);
        return scanner.nextLine().trim();
    }
}
