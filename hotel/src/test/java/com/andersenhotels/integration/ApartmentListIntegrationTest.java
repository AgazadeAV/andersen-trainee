package com.andersenhotels.integration;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApartmentListIntegrationTest {

    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        // Initialize HotelService before each test
        hotelService = new HotelService();
    }

    @Test
    void testListApartments_Success() {
        // Test the successful listing of apartments when apartments are registered
        hotelService.registerApartment(100.0);
        hotelService.registerApartment(200.0);

        List<Apartment> apartments = hotelService.listApartments(1);

        // Verify that two apartments are returned on the first page
        assertEquals(2, apartments.size());
    }

    @Test
    void testListApartments_Empty() {
        // Test that listing an empty page throws an ApartmentNotFoundException
        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.listApartments(1);
        });

        // Verify that the error message indicates no apartments are found on page 1
        assertEquals("No apartments found for the requested page number. Valid page numbers are from 1 to 0.",
                thrown.getMessage());
    }

    @Test
    void testListApartments_InvalidPage() {
        // Test that listing a page that doesn’t exist throws an ApartmentNotFoundException
        hotelService.registerApartment(100.0);

        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.listApartments(2); // Page 2 does not exist
        });

        // Verify that the error message indicates the valid page range
        assertEquals("No apartments found for the requested page number. Valid page numbers are from 1 to 1.",
                thrown.getMessage());
    }
}
