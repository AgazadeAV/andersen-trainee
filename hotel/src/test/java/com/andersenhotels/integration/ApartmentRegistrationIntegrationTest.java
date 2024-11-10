package com.andersenhotels.integration;

import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.presenter.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApartmentRegistrationIntegrationTest {

    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService();
    }

    @Test
    void registerApartment_Success() {
        hotelService.registerApartment(150.0);

        assertEquals(1, hotelService.apartmentsCount());
    }

    @Test
    void registerApartment_NegativePrice() {
        InvalidPriceException thrown = assertThrows(InvalidPriceException.class, () -> {
            hotelService.registerApartment(-100.0);
        });

        assertEquals("The price should be a positive number. Please try again.", thrown.getMessage());
    }

    @Test
    void registerApartment_ZeroPrice() {
        hotelService.registerApartment(0.0);

        assertEquals(1, hotelService.apartmentsCount());
    }
}
