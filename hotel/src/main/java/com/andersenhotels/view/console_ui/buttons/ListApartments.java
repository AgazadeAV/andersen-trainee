package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.View;

public class ListApartments extends Button {

    public ListApartments(View view) {
        super("List Apartments", view);
    }

    @Override
    public void execute() {
        try {
            view.listApartments().forEach(view::displayMessage);
        } catch (ApartmentNotFoundException e) {
            view.displayError(e.getMessage());
        }
    }
}
