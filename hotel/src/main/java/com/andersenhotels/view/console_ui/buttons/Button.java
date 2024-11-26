package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.console_ui.View;
import lombok.Getter;

public abstract class Button {

    @Getter
    protected final String description;
    protected View view;

    protected Button(String description, View view) {
        this.description = description;
        this.view = view;
    }

    public abstract void execute();
}
