package com.andersenhotels.presenter.exceptions;

public class GuestNotFoundException extends RuntimeException {
  public GuestNotFoundException(String message) {
    super(message);
  }
}
