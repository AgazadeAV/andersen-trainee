package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The `Button` class represents an abstract UI button with a description and a reference to the View.
 * This class is intended to be extended by concrete button classes that define specific behavior.
 */
public abstract class Button {
    protected final String description; // Description of the button, typically displayed in the UI
    protected View view; // Reference to the View, allowing the button to communicate with the UI

    /**
     * Constructs a Button with the given description and view reference.
     *
     * @param description A brief description of the button, explaining its purpose.
     * @param view        A reference to the View where the button action will be performed.
     */
    protected Button(String description, View view) {
        this.description = description;
        this.view = view;
    }

    /**
     * Returns the description of the button, which may be displayed in the UI.
     *
     * @return The description of this button.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Abstract method to execute the button's action.
     * Each subclass must define its own implementation of this method, specifying the behavior when the button is pressed.
     */
    public abstract void execute();
}
