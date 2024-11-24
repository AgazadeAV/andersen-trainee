package com.andersenhotels.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {

    @Test
    void constructor_InitializesCollections() {
        Hotel hotel = new Hotel();

        assertNotNull(hotel.getApartments(), "Apartments list should be initialized.");
        assertNotNull(hotel.getReservations(), "Reservations list should be initialized.");
        assertTrue(hotel.getApartments().isEmpty(), "Apartments list should be empty by default.");
        assertTrue(hotel.getReservations().isEmpty(), "Reservations list should be empty by default.");
    }

    @Test
    void toString_Success() {
        Hotel hotel = new Hotel();
        hotel.setId(1);

        String expectedString = "Hotel ID: 1";
        assertEquals(expectedString, hotel.toString());
    }
}
