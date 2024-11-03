package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

public class Exit extends Button {
    public Exit(View view) {
        super("Exit", view);
    }

    @Override
    public void execute() {
        view.finishWork();
    }
}
