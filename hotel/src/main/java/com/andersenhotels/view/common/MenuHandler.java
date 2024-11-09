package com.andersenhotels.view.common;

import com.andersenhotels.view.common.buttons.*;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class MenuHandler {
    private final List<Button> buttons;

    public MenuHandler(View view) {
        buttons = new ArrayList<>();
        buttons.add(new RegisterApartment(view));
        buttons.add(new ReserveApartment(view));
        buttons.add(new ReleaseApartment(view));
        buttons.add(new ListApartments(view));
        buttons.add(new Exit(view));
    }

    public String getMenu() {
        return "Menu:\n" +
                IntStream.range(0, buttons.size())
                        .mapToObj(i -> (i + 1) + ". " + buttons.get(i).getDescription())
                        .collect(Collectors.joining("\n"));
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
