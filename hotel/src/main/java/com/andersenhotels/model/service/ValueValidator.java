package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class ValueValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueValidator.class);

    private final HotelService hotelService;

    ValueValidator(HotelService hotelService) {
        this.hotelService = hotelService;
        LOGGER.info("ValueValidator initialized for HotelService.");
    }

    void validateApartmentId(int id) {
        LOGGER.debug("Validating apartment ID: {}", id);

        if (id < 1 || id > hotelService.apartmentsCount()) {
            LOGGER.warn("Invalid apartment ID: {}. Total apartments: {}", id, hotelService.apartmentsCount());
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Please provide ID between 1 " +
                    "and " + hotelService.apartmentsCount() + ".");
        }

        LOGGER.info("Apartment ID {} is valid.", id);
    }

    void validateGuestName(String guestName) {
        LOGGER.debug("Validating guest name: {}", guestName);

        Optional.ofNullable(guestName)
                .filter(name -> !name.isEmpty() && !Character.isDigit(name.charAt(0)))
                .orElseThrow(() -> {
                    LOGGER.warn("Invalid guest name: '{}'. It is null, empty, or starts with a number.", guestName);
                    return new InvalidNameException("Guest name cannot be null, empty, or start with a number.");
                });

        LOGGER.info("Guest name '{}' is valid.", guestName);
    }
}
