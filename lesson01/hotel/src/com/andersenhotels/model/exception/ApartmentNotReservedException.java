package com.andersenhotels.model.exception;

public class ApartmentNotReservedException extends RuntimeException {
    public ApartmentNotReservedException(String message) {
        super(message);
    }
}
