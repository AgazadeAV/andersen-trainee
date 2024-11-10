package com.andersenhotels.presenter.exceptions;

public class ApartmentAlreadyReservedException extends RuntimeException {

    public ApartmentAlreadyReservedException(String message) {
        super(message);
    }
}
