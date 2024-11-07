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
        // Initialize a new HotelService and register and reserve an apartment before each test
        hotelService = new HotelService();
        hotelService.registerApartment(100.0);
        hotelService.reserveApartment(1, "Azer Agazade");
    }

    @Test
    void testReleaseApartment_Success() {
        // Test successful release of a reserved apartment
        hotelService.releaseApartment(1);

        // Verify that the count of reserved apartments decreases to zero
        assertEquals(0, hotelService.getReservedApartmentsCount());
    }

    @Test
    void testReleaseApartment_NotReserved() {
        // Test releasing an apartment that is not reserved
        hotelService.releaseApartment(1); // First release to make it available

        // Expect ApartmentNotReservedException on second release attempt for the same apartment
        ApartmentNotReservedException thrown = assertThrows(ApartmentNotReservedException.class, () -> {
            hotelService.releaseApartment(1);
        });

        // Verify that the error message indicates the apartment is not reserved
        assertEquals("Apartment is not reserved. Please try again.", thrown.getMessage());
    }

    @Test
    void testReleaseApartment_InvalidId() {
        // Test releasing an apartment with a non-existent ID
        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.releaseApartment(2); // ID 2 does not exist
        });

        // Verify that the error message indicates the valid ID range
        assertEquals("Apartment not found for the given ID. Please provide ID between 1 " +
                "and " + hotelService.getApartmentsCount() + ".", thrown.getMessage());
    }
}
