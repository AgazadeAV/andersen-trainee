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
        // Initialize a new HotelService instance before each test
        hotelService = new HotelService();
    }

    @Test
    void testRegisterApartment_Success() {
        // Test the successful registration of an apartment with a positive price
        hotelService.registerApartment(150.0);

        // Verify that the apartment count increases to 1 after registration
        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void testRegisterApartment_NegativePrice() {
        // Test that an InvalidPriceException is thrown when a negative price is provided
        InvalidPriceException thrown = assertThrows(InvalidPriceException.class, () -> {
            hotelService.registerApartment(-100.0);
        });

        // Verify that the error message corresponds to an invalid negative price
        assertEquals("The price should be a positive number. Please try again.", thrown.getMessage());
    }

    @Test
    void testRegisterApartment_ZeroPrice() {
        // Test the registration of an apartment with a price of zero
        hotelService.registerApartment(0.0);

        // Verify that an apartment is successfully registered with a zero price
        assertEquals(1, hotelService.getApartmentsCount());
    }
}
