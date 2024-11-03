package com.andersenhotels.view.common.exception;

public class WrongMenuChoiceException extends RuntimeException {
    public WrongMenuChoiceException(String message) {
        super(message);
    }
}
