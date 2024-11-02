package view.console_ui.buttons;

import view.console_ui.ConsoleUI;

public class ReleaseApartment extends Button {
    public ReleaseApartment(ConsoleUI consoleUI) {
        super("Release Apartment", consoleUI);
    }

    @Override
    public void execute() {
        getConsoleUI().releaseApartment();
    }
}
