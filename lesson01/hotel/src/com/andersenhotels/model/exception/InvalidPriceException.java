package com.andersenhotels.model.exception;

public class InvalidPriceException extends RuntimeException {
  public InvalidPriceException(String message) {
    super(message);
  }
}
