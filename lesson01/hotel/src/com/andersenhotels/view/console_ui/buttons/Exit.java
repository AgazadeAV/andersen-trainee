package view.console_ui.buttons;

import view.console_ui.ConsoleUI;

public class Exit extends Button {
    public Exit(ConsoleUI consoleUI) {
        super("Exit", consoleUI);
    }

    @Override
    public void execute() {
        getConsoleUI().finishWork();
    }
}
