package com.andersenhotels.view.common.buttons;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.view.common.View;

public class ListApartments extends Button {
    public ListApartments(View view) {
        super("List Apartments", view);
    }

    @Override
    public void execute() {
        try {
            for (String apartment : view.listApartments()) {
                view.displayMessage(apartment);
            }
        } catch (ApartmentNotFoundException e) {
            view.displayError(e.getMessage());
        }
    }
}