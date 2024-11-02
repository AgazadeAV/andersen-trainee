package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The Exit class represents a button for exiting the application.
 * It extends the Button class and defines the action to be executed when the button is pressed.
 */
public class Exit extends Button {
    /**
     * Initializes the Exit button with the associated view.
     *
     * @param view The view instance that will be linked to this button.
     */
    public Exit(View view) {
        super("Exit", view);
    }

    /**
     * Executes the action to finish the application by calling the
     * corresponding method in the associated view.
     */
    @Override
    public void execute() {
        getView().finishWork();
    }
}
