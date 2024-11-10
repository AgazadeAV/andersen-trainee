package com.andersenhotels.integration;

import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApartmentReleaseIntegrationTest {

    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService();
        hotelService.registerApartment(100.0);
        hotelService.reserveApartment(1, "Azer Agazade");
    }

    @Test
    void releaseApartment_Success() {
        hotelService.releaseApartment(1);

        assertEquals(0, hotelService.getReservedApartmentsCount());
    }

    @Test
    void releaseApartment_NotReserved() {
        hotelService.releaseApartment(1);

        ApartmentNotReservedException thrown = assertThrows(ApartmentNotReservedException.class, () -> {
            hotelService.releaseApartment(1);
        });

        assertEquals("Apartment is not reserved. Please try again.", thrown.getMessage());
    }

    @Test
    void releaseApartment_InvalidId() {
        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.releaseApartment(2);
        });

        assertEquals("Apartment not found for the given ID. Please provide ID between 1 " +
                "and " + hotelService.getApartmentsCount() + ".", thrown.getMessage());
    }
}
