package com.andersenhotels.integration;

import com.andersenhotels.model.Hotel;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApartmentReservationIntegrationTest {

    private Hotel hotel;
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotelService = new HotelService(hotel);
        hotelService.registerApartment(100.0);
    }

    @Test
    void reserveApartment_Success() {
        hotelService.reserveApartment(1, "Azer Agazade");

        assertEquals(1, hotelService.getApartmentsCount());
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
                "and " + hotelService.getApartmentsCount() + ".", thrown.getMessage());
    }

    @Test
    void reserveApartment_InvalidGuestName() {
        InvalidNameException thrown = assertThrows(InvalidNameException.class, () -> {
            hotelService.reserveApartment(1, "");
        });

        assertEquals("Guest name cannot be null, empty, or start with a number.", thrown.getMessage());
    }
}
