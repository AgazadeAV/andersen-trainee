package view.console_ui.buttons;

import view.console_ui.ConsoleUI;

public class ListApartments extends Button {
    public ListApartments(ConsoleUI consoleUI) {
        super("List Apartments", consoleUI);
    }

    @Override
    public void execute() {
        getConsoleUI().listApartments();
    }
}
