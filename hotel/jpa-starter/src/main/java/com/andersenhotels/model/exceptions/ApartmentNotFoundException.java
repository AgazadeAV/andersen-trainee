package com.andersenhotels.model.exceptions;

public class ApartmentNotFoundException extends RuntimeException {

    public ApartmentNotFoundException(String message) {
        super(message);
    }
}
