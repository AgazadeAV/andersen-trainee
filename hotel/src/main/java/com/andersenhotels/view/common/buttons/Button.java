package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;
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
