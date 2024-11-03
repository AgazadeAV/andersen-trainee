package com.andersenhotels.view.common.buttons;

import com.andersenhotels.view.common.View;

public class ListApartments extends Button {
    public ListApartments(View view) {
        super("List Apartments", view);
    }

    @Override
    public void execute() {
        view.listApartments();
    }
}
