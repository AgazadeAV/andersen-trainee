package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

public class ReserveApartment extends Button {

    public ReserveApartment(View view) {
        super("Reserve Apartment", view);
    }

    @Override
    public void execute() {
        if (view.reserveApartment()) {
            view.displayMessage("Apartment reserved successfully.");
        }
    }
}
