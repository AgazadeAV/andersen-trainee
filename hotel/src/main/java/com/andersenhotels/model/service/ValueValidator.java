package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.ApartmentStatus;
import com.andersenhotels.presenter.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ValueValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValueValidator.class);

    public static void validateApartmentId(int id, boolean apartmentExists) {
        LOGGER.debug("Validating apartment ID: {}", id);

        if (!apartmentExists) {
            LOGGER.warn("Invalid apartment ID: {}", id);
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Provide a valid ID.");
        }

        LOGGER.info("Apartment ID {} is valid.", id);
    }

    public static void validateGuestName(String guestName) {
        LOGGER.debug("Validating guest name: {}", guestName);

        Optional.ofNullable(guestName)
                .filter(name -> !name.isEmpty() && !Character.isDigit(name.charAt(0)))
                .orElseThrow(() -> {
                    LOGGER.warn("Invalid guest name: '{}'.", guestName);
                    return new InvalidNameException("Guest name must not be null, empty, or start with a number.");
                });

        LOGGER.info("Guest name '{}' is valid.", guestName);
    }

    public static void validatePrice(double price) {
        if (price < 0) {
            LOGGER.warn("Attempt to register an apartment with a negative price: {}", price);
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }
    }

    public static void validatePageNumber(int page, int totalPages) {
        if (page <= 0 || page > totalPages) {
            throw new ApartmentNotFoundException("Invalid page number.");
        }
    }

    public static void validateApartmentStatus(Apartment apartment, ApartmentStatus requiredStatus) {
        if (apartment.getStatus() != requiredStatus) {
            LOGGER.warn("Apartment ID {} has invalid status: {}. Required: {}",
                    apartment.getId(), apartment.getStatus(), requiredStatus);
            throw new ApartmentNotReservedException("Apartment does not meet the required status.");
        }
    }
}
