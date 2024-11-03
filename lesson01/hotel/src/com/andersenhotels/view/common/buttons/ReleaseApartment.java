package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

public class ReleaseApartment extends Button {
    public ReleaseApartment(View view) {
        super("Release Apartment", view);
    }

    @Override
    public void execute() {
        view.releaseApartment();
    }
}
