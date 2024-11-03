package com.andersenhotels.model.exception;

public class InvalidApartmentIdException extends RuntimeException {
    public InvalidApartmentIdException(String message) {
        super(message);
    }
}
