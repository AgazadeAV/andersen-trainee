package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueValidatorTest {

    private HotelService hotelService; // Instance of HotelService to register apartments for testing
    private ValueValidator valueValidator; // Instance of ValueValidator to validate inputs

    @BeforeEach
    void setUp() {
        // Initialize HotelService and ValueValidator before each test
        hotelService = new HotelService();
        valueValidator = new ValueValidator(hotelService);
    }

    @Test
    void testValidateApartmentId_ValidId() {
        // Register an apartment to ensure there's an ID to validate
        hotelService.registerApartment(100.0);

        // No exception is expected for a valid apartment ID
        valueValidator.validateApartmentId(1);
    }

    @Test
    void testValidateApartmentId_InvalidId() {
        // Register an apartment to define a valid ID range
        hotelService.registerApartment(100.0);

        // Expect an InvalidApartmentIdException for an ID not in the system
        assertThrows(ApartmentNotFoundException.class, () -> valueValidator.validateApartmentId(999));
    }

    @Test
    void testValidateGuestName_ValidName() {
        // Validate a properly formatted name; no exception is expected
        valueValidator.validateGuestName("Azer Agazade");
    }

    @Test
    void testValidateGuestName_NullName() {
        // Expect an InvalidNameException when the name is null
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName(null));
    }

    @Test
    void testValidateGuestName_EmptyName() {
        // Expect an InvalidNameException when the name is empty
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName(""));
    }

    @Test
    void testValidateGuestName_StartsWithDigit() {
        // Expect an InvalidNameException when the name starts with a digit
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName("1Azer Agazade"));
    }
}
