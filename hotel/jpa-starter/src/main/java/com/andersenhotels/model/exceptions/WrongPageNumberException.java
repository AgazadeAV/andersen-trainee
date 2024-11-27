package com.andersenhotels.model.exceptions;

public class WrongPageNumberException extends RuntimeException {

    public WrongPageNumberException(String message) {
        super(message);
    }
}
