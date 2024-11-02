package com.andersenhotels.view.console_ui;

import com.andersenhotels.view.console_ui.buttons.*;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {
    private List<Button> buttons;

    public MenuHandler(ConsoleUI consoleUI) {
        buttons = new ArrayList<>();
        buttons.add(new RegisterApartment(consoleUI));
        buttons.add(new ReserveApartment(consoleUI));
        buttons.add(new ReleaseApartment(consoleUI));
        buttons.add(new ListApartments(consoleUI));
        buttons.add(new Exit(consoleUI));
    }

    public String getMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Please select an option from the menu.\nMenu:\n");
        for (int i = 0; i < buttons.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(buttons.get(i).getDescription())
                    .append("\n");
        }
        return sb.toString();
    }

    public void execute(int choice) {
        Button button = buttons.get(choice - 1);
        button.execute();
    }

    public int getMenuSize() {
        return buttons.size();
    }
}
