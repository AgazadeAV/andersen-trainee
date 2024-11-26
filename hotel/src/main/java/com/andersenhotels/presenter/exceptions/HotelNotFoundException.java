package com.andersenhotels.presenter.exceptions;

public class HotelNotFoundException extends RuntimeException {

  public HotelNotFoundException(String message) {
    super(message);
  }
}
