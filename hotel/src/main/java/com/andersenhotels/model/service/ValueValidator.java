package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;

/**
 * A utility class responsible for validating various input values for the hotel services.
 * Ensures inputs such as apartment IDs and guest names meet specific criteria, enhancing data integrity.
 * Throws custom exceptions when validation fails, enabling clear error handling in the application.
 */
class ValueValidator {
    private HotelService hotelService;

    /**
     * Constructs a new ValueValidator instance with the specified HotelService dependency.
     *
     * @param hotelService The hotel service instance that provides access to hotel-specific data,
     *                     such as the total number of registered apartments.
     *                     This dependency must be initialized and non-null.
     */
    ValueValidator(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    /**
     * Validates an apartment ID to confirm it is within the valid range of existing apartments.
     * The valid range is determined by the current number of apartments in the HotelService instance.
     *
     * @param id The ID of the apartment to validate. Expected to be a positive integer within the
     *           range of available apartment IDs.
     * @throws ApartmentNotFoundException if the ID is less than 1 or exceeds the current total apartment count.
     *                                     The exception message specifies the acceptable range to guide user input.
     */
    void validateApartmentId(int id) {
        if (id < 1 || id > hotelService.getApartmentsCount()) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Please provide ID between 1 " +
                    "and " + hotelService.getApartmentsCount() + ".");
        }
    }

    /**
     * Validates a guest name to ensure it conforms to specific criteria:
     * - The name must not be null or empty.
     * - The name should not start with a digit, ensuring names are meaningful and readable.
     * This validation prevents unintended formatting issues and helps maintain data consistency for guest names.
     *
     * @param guestName The name of the guest to validate. Expected to be a non-null, non-empty string
     *                  that does not start with a numeric character.
     * @throws InvalidNameException if the guest name is null, empty, or begins with a digit.
     *                              Provides feedback on why the name is considered invalid.
     */
    void validateGuestName(String guestName) {
        if (guestName == null || guestName.isEmpty() || Character.isDigit(guestName.charAt(0))) {
            throw new InvalidNameException("Guest name cannot be null, empty, or start with a number.");
        }
    }
}
