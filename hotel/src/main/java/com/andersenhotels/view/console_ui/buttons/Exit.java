package com.andersenhotels.view.console_ui.buttons;

import com.andersenhotels.view.View;

public class Exit extends Button {

    public Exit(View view) {
        super("Exit", view);
    }

    @Override
    public void execute() {
        view.complete();
    }
}
