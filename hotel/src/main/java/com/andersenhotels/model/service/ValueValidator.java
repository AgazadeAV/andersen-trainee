package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ValueValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueValidator.class);
    private final HotelService hotelService;

    public ValueValidator(HotelService hotelService) {
        this.hotelService = hotelService;
        LOGGER.info("ValueValidator initialized.");
    }

    public void validateApartmentId(int id) {
        LOGGER.debug("Validating apartment ID: {}", id);

        if (!hotelService.apartmentExists(id)) {
            LOGGER.warn("Invalid apartment ID: {}", id);
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Provide a valid ID.");
        }

        LOGGER.info("Apartment ID {} is valid.", id);
    }

    public void validateGuestName(String guestName) {
        LOGGER.debug("Validating guest name: {}", guestName);

        Optional.ofNullable(guestName)
                .filter(name -> !name.isEmpty() && !Character.isDigit(name.charAt(0)))
                .orElseThrow(() -> {
                    LOGGER.warn("Invalid guest name: '{}'.", guestName);
                    return new InvalidNameException("Guest name must not be null, empty, or start with a number.");
                });

        LOGGER.info("Guest name '{}' is valid.", guestName);
    }
}
