package com.andersenhotels.view.common;

import com.andersenhotels.view.common.buttons.*;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;

import java.util.ArrayList;
import java.util.List;

public class MenuHandler {
    private List<Button> buttons;

    public MenuHandler(View view) {
        buttons = new ArrayList<>();
        buttons.add(new RegisterApartment(view));
        buttons.add(new ReserveApartment(view));
        buttons.add(new ReleaseApartment(view));
        buttons.add(new ListApartments(view));
        buttons.add(new Exit(view));
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public String getMenu() {
        StringBuilder sb = new StringBuilder();
        sb.append("Menu:\n");
        for (int i = 0; i < buttons.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(buttons.get(i).getDescription())
                    .append("\n");
        }
        return sb.toString();
    }

    public void execute(int choice) {
        if (choice >= 1 && choice <= getMenuSize()) {
            Button button = buttons.get(choice - 1);
            button.execute();
        } else {
            throw new WrongMenuChoiceException("Invalid menu option entered. " +
                    "Please enter a valid number from the menu: from 1 to " + getMenuSize() + ".");
        }
    }

    public int getMenuSize() {
        return buttons.size();
    }
}
