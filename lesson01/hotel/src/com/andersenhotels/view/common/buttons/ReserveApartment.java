package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The ReserveApartment class represents a button for reserving an apartment.
 * It extends the Button class and defines the action to be executed when the button is pressed.
 */
public class ReserveApartment extends Button {
    /**
     * Initializes the ReserveApartment button with the associated view.
     *
     * @param view The view instance that will be linked to this button.
     */
    public ReserveApartment(View view) {
        super("Reserve Apartment", view);
    }

    /**
     * Executes the action to reserve an apartment by calling the
     * corresponding method in the associated view.
     */
    @Override
    public void execute() {
        getView().reserveApartment();
    }
}
