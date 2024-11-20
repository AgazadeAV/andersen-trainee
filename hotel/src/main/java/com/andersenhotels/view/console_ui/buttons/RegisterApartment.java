package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.View;

public class RegisterApartment extends Button {

    public RegisterApartment(View view) {
        super("Register Apartment", view);
    }

    @Override
    public void execute() {
        if (view.registerApartment()) {
            view.displayMessage("Apartment registered successfully.");
        }
    }
}
