package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.View;

public class ReleaseApartment extends Button {

    public ReleaseApartment(View view) {
        super("Release Apartment", view);
    }

    @Override
    public void execute() {
        if (view.releaseApartment()) {
            view.displayMessage("Apartment released successfully.");
        }
    }
}
