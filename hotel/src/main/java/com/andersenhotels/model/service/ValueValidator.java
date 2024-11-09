package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;

//TODO make methods in this class static, remove fields, because method that is using here
// also will be static in another class
class ValueValidator {
    private HotelService hotelService;

    ValueValidator(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    void validateApartmentId(int id) {
        if (id < 1 || id > hotelService.getApartmentsCount()) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Please provide ID between 1 " +
                    "and " + hotelService.getApartmentsCount() + ".");
        }
    }

    void validateGuestName(String guestName) {
        if (guestName == null || guestName.isEmpty() || Character.isDigit(guestName.charAt(0))) {
            throw new InvalidNameException("Guest name cannot be null, empty, or start with a number.");
        }
    }
}
