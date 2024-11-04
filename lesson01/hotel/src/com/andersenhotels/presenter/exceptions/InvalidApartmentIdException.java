package com.andersenhotels.presenter.exceptions;

public class InvalidApartmentIdException extends RuntimeException {
    public InvalidApartmentIdException(String message) {
        super(message);
    }
}
