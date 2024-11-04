package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.InvalidApartmentIdException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;

/**
 * A utility class responsible for validating various values related to hotel services, such as
 * apartment IDs and guest names. Ensures that the inputs meet specified criteria and throws
 * exceptions if validation fails.
 */
class ValueValidator {
    private HotelService hotelService;

    /**
     * Constructs a new ValueValidator instance with the specified HotelService dependency.
     *
     * @param hotelService The hotel service to use for retrieving hotel-specific information,
     *                     such as the total number of apartments.
     *                     It cannot be null and should be properly initialized.
     */
    ValueValidator(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Validates the apartment ID to ensure it is within the valid range.
     *
     * @param id The ID of the apartment to validate.
     * @throws InvalidApartmentIdException if the ID is out of range.
     */
    void validateApartmentId(int id) {
        if (id < 1 || id > hotelService.getApartmentsCount()) {
            throw new InvalidApartmentIdException("Invalid apartment ID. Please enter a number between 1 and " +
                    hotelService.getApartmentsCount() + ".");
        }
    }

    /**
     * Validates the guest name to ensure it is not null, empty, or starts with a digit.
     *
     * @param guestName The name of the guest to validate.
     * @throws InvalidNameException if the guest name is null, empty, or starts with a number.
     */
    void validateGuestName(String guestName) {
        if (guestName == null || guestName.isEmpty() || Character.isDigit(guestName.charAt(0))) {
            throw new InvalidNameException("Guest name cannot be null or start with a number.");
        }
    }
}
