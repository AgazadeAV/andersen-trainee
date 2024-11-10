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
        hotelService = new HotelService();
        hotelService.registerApartment(100.0);
    }

    @Test
    void reserveApartment_Success() {
        hotelService.reserveApartment(1, "Azer Agazade");

        assertEquals(1, hotelService.apartmentsCount());
    }

    @Test
    void reserveApartment_AlreadyReserved() {
        hotelService.reserveApartment(1, "Azer Agazade");

        ApartmentAlreadyReservedException thrown = assertThrows(ApartmentAlreadyReservedException.class, () -> {
            hotelService.reserveApartment(1, "Azer Agazade");
        });

        assertEquals("Apartment is already reserved.", thrown.getMessage());
    }

    @Test
    void reserveApartment_InvalidId() {
        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.reserveApartment(2, "Azer Agazade");
        });

        assertEquals("Apartment not found for the given ID. Please provide ID between 1 " +
                "and " + hotelService.apartmentsCount() + ".", thrown.getMessage());
    }

    @Test
    void reserveApartment_InvalidGuestName() {
        InvalidNameException thrown = assertThrows(InvalidNameException.class, () -> {
            hotelService.reserveApartment(1, "");
        });

        assertEquals("Guest name cannot be null, empty, or start with a number.", thrown.getMessage());
    }
}
