package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The ReleaseApartment class represents a button for releasing an apartment.
 * It extends the Button class and defines the action to be executed when the button is pressed.
 */
public class ReleaseApartment extends Button {
    /**
     * Initializes the ReleaseApartment button with the associated view.
     *
     * @param view The view instance that will be linked to this button.
     */
    public ReleaseApartment(View view) {
        super("Release Apartment", view);
    }

    /**
     * Executes the action to release an apartment by calling the
     * corresponding method in the associated view.
     */
    @Override
    public void execute() {
        getView().releaseApartment();
    }
}
