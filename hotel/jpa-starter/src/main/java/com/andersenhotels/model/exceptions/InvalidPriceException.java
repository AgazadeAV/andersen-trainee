package com.andersenhotels.model.exceptions;

public class InvalidPriceException extends RuntimeException {

    public InvalidPriceException(String message) {
        super(message);
    }
}
