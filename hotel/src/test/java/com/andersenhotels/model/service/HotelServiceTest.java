package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.presenter.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceTest {

    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService();
    }

    @Test
    void registerApartment_Success() {
        hotelService.registerApartment(100.0);

        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void registerApartment_InvalidPrice() {
        assertThrows(InvalidPriceException.class, () -> hotelService.registerApartment(-50.0));
    }

    @Test
    void reserveApartment_Success() {
        hotelService.registerApartment(100.0);
        hotelService.reserveApartment(1, "Azer Agazade");

        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void reserveApartment_ApartmentNotFound() {
        assertThrows(ApartmentNotFoundException.class, () ->
                hotelService.reserveApartment(999, "Azer Agazade"));
    }

    @Test
    void reserveApartment_InvalidName() {
        hotelService.registerApartment(100.0);

        assertThrows(InvalidNameException.class, () -> hotelService.reserveApartment(1, ""));
    }

    @Test
    void releaseApartment_Success() {
        hotelService.registerApartment(100.0);
        hotelService.reserveApartment(1, "Azer Agazade");
        hotelService.releaseApartment(1);

        assertEquals(1, hotelService.getApartmentsCount());
    }

    @Test
    void releaseApartment_NotReserved() {

        hotelService.registerApartment(100.0);
        assertThrows(ApartmentNotReservedException.class, () -> hotelService.releaseApartment(1));
    }

    @Test
    void listApartments_Success() {
        for (int i = 0; i < 7; i++) {
            hotelService.registerApartment(100.0 + i);
        }

        List<Apartment> apartmentsPage1 = hotelService.listApartments(1);
        List<Apartment> apartmentsPage2 = hotelService.listApartments(2);

        assertEquals(5, apartmentsPage1.size());
        assertEquals(2, apartmentsPage2.size());
    }

    @Test
    void listApartments_InvalidPage() {
        assertThrows(ApartmentNotFoundException.class, () -> hotelService.listApartments(-1));
    }

    @Test
    void getTotalPages() {
        for (int i = 0; i < 12; i++) {
            hotelService.registerApartment(100.0 + i);
        }

        assertEquals(3, hotelService.getTotalPages());
    }
}
