package com.andersenhotels.view;

import java.util.List;

public interface View {

    void initialize();

    void complete();

    boolean registerApartment();

    boolean reserveApartment();

    boolean releaseApartment();

    List<String> listApartments();

    void displayMessage(String message);

    void displayError(String errorMessage);
}
