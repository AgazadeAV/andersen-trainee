package com.andersenhotels.model.service;

import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueValidatorTest {

    private HotelService hotelService;
    private ValueValidator valueValidator;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService();
        valueValidator = new ValueValidator(hotelService);
    }

    @Test
    void validateApartmentId_ValidId() {
        hotelService.registerApartment(100.0);

        valueValidator.validateApartmentId(1);
    }

    @Test
    void validateApartmentId_InvalidId() {
        hotelService.registerApartment(100.0);

        assertThrows(ApartmentNotFoundException.class, () -> valueValidator.validateApartmentId(999));
    }

    @Test
    void validateGuestName_ValidName() {
        valueValidator.validateGuestName("Azer Agazade");
    }

    @Test
    void validateGuestName_NullName() {
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName(null));
    }

    @Test
    void validateGuestName_EmptyName() {
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName(""));
    }

    @Test
    void validateGuestName_StartsWithDigit() {
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName("1Azer Agazade"));
    }
}
