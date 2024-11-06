package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

/**
 * The `Button` class represents an abstract UI button with a description and a reference to the `View`.
 * This class is intended as a base for specific types of buttons, each of which will have its own action.
 * Concrete button classes extending `Button` should implement the `execute` method to define the button's
 * unique behavior when triggered.
 * <p>
 * The `Button` class provides a foundation for creating various interactive buttons within the UI.
 * Each button is associated with a description that clarifies its function and a reference to the
 * `View`, allowing it to interact with the user interface.
 */
public abstract class Button {
    protected final String description;
    protected View view;

    /**
     * Constructs a new Button instance with a given description and a reference to the View.
     * This constructor is intended to be called by subclasses representing specific button types.
     *
     * @param description A short text describing the button’s function. This text is often used as
     *                    a label or tooltip in the UI to inform users of the button’s purpose.
     * @param view        A reference to the `View` instance that the button will interact with,
     *                    allowing the button to display messages, retrieve input, or update the UI.
     */
    protected Button(String description, View view) {
        this.description = description;
        this.view = view;
    }

    /**
     * Retrieves the description of the button.
     * This description provides context for the button’s function and may be displayed in the UI.
     *
     * @return The description of this button, as a `String`.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Abstract method that defines the action to be performed when the button is pressed.
     * Each concrete subclass must implement this method to specify the button's specific behavior.
     * This allows for flexible button actions across different UI components.
     */
    public abstract void execute();
}
