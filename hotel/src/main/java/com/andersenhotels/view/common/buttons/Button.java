package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

public abstract class Button {
    protected final String description;
    protected View view;

    protected Button(String description, View view) {
        this.description = description;
        this.view = view;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute();
}
