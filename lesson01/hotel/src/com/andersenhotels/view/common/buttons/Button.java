package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The abstract Button class serves as a base for specific button implementations
 * in the hotel management application. It encapsulates the common behavior
 * and properties of buttons that trigger user actions.
 */
public abstract class Button {
    private final String description;
    private View view;

    /**
     * Initializes a new Button with the given description and associated view.
     *
     * @param description A string that describes the button's action.
     * @param view The view instance that will be associated with this button.
     */
    public Button(String description, View view) {
        this.description = description;
        this.view = view;
    }

    /**
     * Returns the associated view instance.
     *
     * @return The view associated with this button.
     */
    protected View getView() {
        return view;
    }

    /**
     * Returns a description of the button.
     *
     * @return A string representing the button's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Executes the action associated with this button. Concrete subclasses
     * must provide an implementation for this method.
     */
    public abstract void execute();
}
