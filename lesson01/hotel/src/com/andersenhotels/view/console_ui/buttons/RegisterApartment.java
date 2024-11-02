package view.console_ui.buttons;

import view.console_ui.ConsoleUI;

public class RegisterApartment extends Button {
    public RegisterApartment(ConsoleUI consoleUI) {
        super("Register Apartment", consoleUI);
    }

    @Override
    public void execute() {
        getConsoleUI().registerApartment();
    }
}
