package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.HotelNotFoundException;
import com.andersenhotels.presenter.exceptions.InvalidPriceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApartmentTest {

    @Test
    void constructor_ThrowsHotelNotFoundException() {
        Exception exception = assertThrows(HotelNotFoundException.class, () -> new Apartment(100.0, null));
        assertEquals("Hotel cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsInvalidPriceException() {
        Hotel hotel = new Hotel();
        Exception exception = assertThrows(InvalidPriceException.class, () -> new Apartment(-50.0, hotel));
        assertEquals("Price cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_ValidApartment() {
        Hotel hotel = new Hotel();
        Apartment apartment = new Apartment(200.0, hotel);

        assertEquals(200.0, apartment.getPrice());
        assertEquals(ApartmentStatus.AVAILABLE, apartment.getStatus());
        assertEquals(hotel, apartment.getHotel());
    }
}
