package com.andersenhotels.view.common;

import com.andersenhotels.view.common.buttons.*;
import com.andersenhotels.presenter.exceptions.WrongMenuChoiceException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Optional.of(choice)
                .filter(c -> c >= 1 && c <= getMenuSize())
                .map(c -> buttons.get(c - 1))
                .ifPresentOrElse(Button::execute, () -> {
                    throw new WrongMenuChoiceException("Invalid menu option entered. " +
                            "Please enter a valid number from the menu: from 1 to " + getMenuSize() + ".");
                });
    }

    public int getMenuSize() {
        return buttons.size();
    }
}
