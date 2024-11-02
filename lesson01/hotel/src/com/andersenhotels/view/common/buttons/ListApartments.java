package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The ListApartments class represents a button for listing apartments.
 * It extends the Button class and defines the action to be executed when the button is pressed.
 */
public class ListApartments extends Button {
    /**
     * Initializes the ListApartments button with the associated view.
     *
     * @param view The view instance that will be linked to this button.
     */
    public ListApartments(View view) {
        super("List Apartments", view);
    }

    /**
     * Executes the action to list apartments by calling the
     * corresponding method in the associated view.
     */
    @Override
    public void execute() {
        getView().listApartments();
    }
}
