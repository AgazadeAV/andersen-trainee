package com.andersenhotels.model.service;

import com.andersenhotels.model.Hotel;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValueValidatorTest {

    private Hotel hotel;
    private HotelService hotelService;
    private ValueValidator valueValidator;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotelService = new HotelService(hotel);
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

    @ParameterizedTest
    @ValueSource(strings = { "", "1Azer Agazade" })
    void validateGuestName_InvalidNames(String invalidName) {
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName(invalidName));
    }

    @Test
    void validateGuestName_NullName() {
        assertThrows(InvalidNameException.class, () -> valueValidator.validateGuestName(null));
    }
}
