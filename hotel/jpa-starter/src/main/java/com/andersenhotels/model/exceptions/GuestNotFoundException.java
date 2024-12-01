package com.andersenhotels.model.exceptions;

public class GuestNotFoundException extends RuntimeException {

  public GuestNotFoundException(String message) {
    super(message);
  }
}
