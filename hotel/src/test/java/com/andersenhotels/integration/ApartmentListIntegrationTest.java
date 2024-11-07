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
        hotelService = new HotelService();
    }

    @Test
    void listApartments_Success() {
        hotelService.registerApartment(100.0);
        hotelService.registerApartment(200.0);

        List<Apartment> apartments = hotelService.listApartments(1);

        assertEquals(2, apartments.size());
    }

    @Test
    void listApartments_Empty() {
        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.listApartments(1);
        });

        assertEquals("No apartments found for the requested page number. Valid page numbers are from 1 to 0.",
                thrown.getMessage());
    }

    @Test
    void listApartments_InvalidPage() {
        hotelService.registerApartment(100.0);

        ApartmentNotFoundException thrown = assertThrows(ApartmentNotFoundException.class, () -> {
            hotelService.listApartments(2);
        });

        assertEquals("No apartments found for the requested page number. Valid page numbers are from 1 to 1.",
                thrown.getMessage());
    }
}
