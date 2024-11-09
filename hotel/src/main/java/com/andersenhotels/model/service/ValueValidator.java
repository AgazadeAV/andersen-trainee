package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;

import java.util.Optional;

class ValueValidator {
    private final HotelService hotelService;

    ValueValidator(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    void validateApartmentId(int id) {
        if (id < 1 || id > hotelService.apartmentsCount()) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Please provide ID between 1 " +
                    "and " + hotelService.apartmentsCount() + ".");
        }
    }

    void validateGuestName(String guestName) {
        Optional.ofNullable(guestName)
                .filter(name -> !name.isEmpty() && !Character.isDigit(name.charAt(0)))
                .orElseThrow(() -> new InvalidNameException("Guest name cannot be null, empty, or start with a number."));
    }
}
