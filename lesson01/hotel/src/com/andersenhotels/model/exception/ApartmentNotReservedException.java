package com.andersenhotels.model.exception;

/**
 * Exception thrown when an attempt is made to access or modify an apartment
 * that has not been reserved.
 */
public class ApartmentNotReservedException extends RuntimeException {

    /**
     * Constructs a new ApartmentNotReservedException with the specified detail message.
     *
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public ApartmentNotReservedException(String message) {
        super(message);
    }
}
