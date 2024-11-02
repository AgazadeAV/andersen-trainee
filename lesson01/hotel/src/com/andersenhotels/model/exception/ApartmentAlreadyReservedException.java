package com.andersenhotels.model.exception;

public class ApartmentAlreadyReservedException extends RuntimeException {
  public ApartmentAlreadyReservedException(String message) {
    super(message);
  }
}
