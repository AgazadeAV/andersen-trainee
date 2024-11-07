package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.presenter.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceTest {

    private HotelService hotelService; // Instance of HotelService to test its functionalities

    @BeforeEach
    void setUp() {
        // Initialize HotelService before each test to ensure a clean state
        hotelService = new HotelService();
    }

    @Test
    void testRegisterApartment_Success() {
        // Test successful registration of an apartment
        hotelService.registerApartment(100.0);

        // Verify that the count of apartments has increased by one
        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void testRegisterApartment_InvalidPrice() {
        // Verify that an InvalidPriceException is thrown for negative price
        assertThrows(InvalidPriceException.class, () -> hotelService.registerApartment(-50.0));
    }

    @Test
    void testReserveApartment_Success() {
        // Register an apartment and reserve it successfully
        hotelService.registerApartment(100.0);
        hotelService.reserveApartment(1, "Azer Agazade");

        // Verify that the apartment count remains the same after reservation
        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void testReserveApartment_ApartmentNotFound() {
        // Verify that reserving a non-existent apartment throws InvalidApartmentIdException
        assertThrows(ApartmentNotFoundException.class, () -> hotelService.reserveApartment(999, "Azer Agazade"));
    }

    @Test
    void testReserveApartment_InvalidName() {
        // Register an apartment and try to reserve it with an invalid name
        hotelService.registerApartment(100.0);

        // Verify that an InvalidNameException is thrown for an empty guest name
        assertThrows(InvalidNameException.class, () -> hotelService.reserveApartment(1, ""));
    }

    @Test
    void testReleaseApartment_Success() {
        // Register and reserve an apartment, then release it successfully
        hotelService.registerApartment(100.0);
        hotelService.reserveApartment(1, "Azer Agazade");
        hotelService.releaseApartment(1);

        // Verify that the apartment count remains the same after release
        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void testReleaseApartment_NotReserved() {
        // Register an apartment but do not reserve it

        // Verify that releasing a non-reserved apartment throws ApartmentNotReservedException
        hotelService.registerApartment(100.0);
        assertThrows(ApartmentNotReservedException.class, () -> hotelService.releaseApartment(1));
    }

    @Test
    void testListApartments_Success() {
        // Register multiple apartments to test pagination
        for (int i = 0; i < 7; i++) {
            hotelService.registerApartment(100.0 + i);
        }

        // Verify that the first page contains 5 apartments and the second page contains the remaining 2
        List<Apartment> apartmentsPage1 = hotelService.listApartments(1);
        List<Apartment> apartmentsPage2 = hotelService.listApartments(2);

        assertEquals(5, apartmentsPage1.size());
        assertEquals(2, apartmentsPage2.size());
    }

    @Test
    void testListApartments_InvalidPage() {
        // Verify that an invalid page number throws ApartmentNotFoundException
        assertThrows(ApartmentNotFoundException.class, () -> hotelService.listApartments(-1));
    }

    @Test
    void testGetTotalPages() {
        // Register multiple apartments and verify the total number of pages
        for (int i = 0; i < 12; i++) {
            hotelService.registerApartment(100.0 + i);
        }

        // Verify that there are 3 pages when there are 12 apartments (assuming 5 per page)
        assertEquals(3, hotelService.getTotalPages());
    }
}
