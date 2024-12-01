package com.andersenhotels.model.exceptions;

public class ApartmentAlreadyReservedException extends RuntimeException {

    public ApartmentAlreadyReservedException(String message) {
        super(message);
    }
}
