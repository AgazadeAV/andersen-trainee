package com.andersenhotels.presenter.exceptions;

public class ApartmentNotReservedException extends RuntimeException {

    public ApartmentNotReservedException(String message) {
        super(message);
    }
}
