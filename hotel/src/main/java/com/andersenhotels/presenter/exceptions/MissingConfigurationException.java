package com.andersenhotels.presenter.exceptions;

public class MissingConfigurationException extends RuntimeException {

    public MissingConfigurationException(String message) {
        super(message);
    }

    public MissingConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
