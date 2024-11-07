package com.andersenhotels.integration;

import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApartmentReservationIntegrationTest {

    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        // Initialize a new HotelService instance and register an apartment before each test
        hotelService = new HotelService();
        hotelService.registerApartment(100.0);
    }

    @Test
    void testReserveApartment_Success() {
        // Test the successful reservation of an apartment with a valid ID and guest name
        hotelService.reserveApartment(1, "Azer Agazade");

        // Verify that the apartment count remains as expected after reservation
        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void testReserveApartment_AlreadyReserved() {
        // Test that reserving an already reserved apartment throws an ApartmentAlreadyReservedException
        hotelService.reserveApartment(1, "Azer Agazade");

        ApartmentAlreadyReservedException thrown = assertThrows(ApartmentAlreadyReservedException.class, () -> {
            hotelService.reserveApartment(1, "Azer Agazade");
        });

        // Verify that the error message specifies the apartment is already reserved
        assertEquals("Apartment is already reserved by Azer Agazade.", thrown.getMessage());
    }

    @Test
    void testReserveApartment_InvalidId() {
        // Test that reserving an apartment with a non-existent ID throws an ApartmentNotFoundException
        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.reserveApartment(2, "Azer Agazade");
        });

        // Verify that the error message indicates the valid range for apartment IDs
        assertEquals("Apartment not found for the given ID. Please provide ID between 1 " +
                "and " + hotelService.getApartmentsCount() + ".", thrown.getMessage());
    }

    @Test
    void testReserveApartment_InvalidGuestName() {
        // Test that providing an invalid guest name throws an InvalidNameException
        InvalidNameException thrown = assertThrows(InvalidNameException.class, () -> {
            hotelService.reserveApartment(1, ""); // Invalid guest name
        });

        // Verify that the error message explains the guest name requirements
        assertEquals("Guest name cannot be null, empty, or start with a number.", thrown.getMessage());
    }
}
