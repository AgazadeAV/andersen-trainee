package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.common.View;

import java.util.Scanner;

public class InputValidator {
    private Scanner scanner;
    private View view;

    public InputValidator(View view) {
        this.scanner = new Scanner(System.in);
        this.view = view;
    }

    public InputValidator(View view, Scanner scanner) {
        this.scanner = scanner;
        this.view = view;
    }

    public int getIntInput(String prompt) {
        while (true) {
            view.displayMessage(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid integer value. Please try again.");
            }
        }
    }

    public double getDoubleInput(String prompt) {
        while (true) {
            view.displayMessage(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                view.displayError("Invalid number. Please enter a valid double or integer value.");
            }
        }
    }

    public String getStringInput(String prompt) {
        view.displayMessage(prompt);
        return scanner.nextLine().trim();
    }
}
