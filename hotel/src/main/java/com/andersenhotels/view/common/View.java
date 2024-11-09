package com.andersenhotels.view.common;

import java.util.List;

public interface View {

    void startWork();

    void finishWork();

    boolean registerApartment();

    boolean reserveApartment();

    boolean releaseApartment();

    List<String> listApartments();

    void displayMessage(String message);

    void displayError(String errorMessage);
}
