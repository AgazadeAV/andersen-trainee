package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The RegisterApartment class represents a button for registering a new apartment.
 * It extends the Button class and defines the action to be executed when the button is pressed.
 */
public class RegisterApartment extends Button {
    /**
     * Initializes the RegisterApartment button with the associated view.
     *
     * @param view The view instance that will be linked to this button.
     */
    public RegisterApartment(View view) {
        super("Register Apartment", view);
    }

    /**
     * Executes the action to register a new apartment by calling the
     * corresponding method in the associated view.
     */
    @Override
    public void execute() {
        getView().registerApartment();
    }
}
