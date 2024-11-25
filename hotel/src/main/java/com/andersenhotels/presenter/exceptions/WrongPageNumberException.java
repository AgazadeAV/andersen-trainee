package com.andersenhotels.presenter.exceptions;

public class WrongPageNumberException extends RuntimeException {
    public WrongPageNumberException(String message) {
        super(message);
    }
}
