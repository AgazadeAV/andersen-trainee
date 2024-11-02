package com.andersenhotels.model.exception;

/**
 * Exception thrown when an attempt is made to reserve an apartment
 * that has already been reserved.
 */
public class ApartmentAlreadyReservedException extends RuntimeException {

  /**
   * Constructs a new ApartmentAlreadyReservedException with the specified detail message.
   *
   * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method.
   */
  public ApartmentAlreadyReservedException(String message) {
    super(message);
  }
}
