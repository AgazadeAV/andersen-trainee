package view.console_ui.buttons;

import view.console_ui.ConsoleUI;

public class ReserveApartment extends Button {
    public ReserveApartment(ConsoleUI consoleUI) {
        super("Reserve Apartment", consoleUI);
    }

    @Override
    public void execute() {
        getConsoleUI().reserveApartment();
    }
}
