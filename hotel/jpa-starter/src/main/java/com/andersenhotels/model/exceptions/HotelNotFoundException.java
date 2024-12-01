package com.andersenhotels.model.exceptions;

public class HotelNotFoundException extends RuntimeException {

  public HotelNotFoundException(String message) {
    super(message);
  }
}
