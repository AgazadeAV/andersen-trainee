package com.andersenhotels.view.console_ui;

import java.util.List;

public interface View {

    void initialize();

    boolean registerApartment();

    boolean reserveApartment();

    boolean releaseApartment();

    List<String> listApartments();

    void displayMessage(String message);

    void displayError(String errorMessage);

    void complete();
}
