package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.GuestNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationTest {

    @Test
    void constructor_ThrowsApartmentNotFoundException() {
        Guest guest = mock(Guest.class);

        Exception exception = assertThrows(ApartmentNotFoundException.class, () -> new Reservation(null, guest));
        assertEquals("Apartment cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsGuestNotFoundException() {
        Apartment apartment = mock(Apartment.class);

        Exception exception = assertThrows(GuestNotFoundException.class, () -> new Reservation(apartment, null));
        assertEquals("Guest cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_ThrowsApartmentAlreadyReservedException() {
        Apartment apartment = mock(Apartment.class);
        Guest guest = mock(Guest.class);

        when(apartment.getStatus()).thenReturn(ApartmentStatus.RESERVED);

        Exception exception = assertThrows(ApartmentAlreadyReservedException.class, () -> new Reservation(apartment, guest));
        assertEquals("Apartment must have status AVAILABLE to create a reservation.", exception.getMessage());
    }

    @Test
    void constructor_ValidReservation() {
        Apartment apartment = mock(Apartment.class);
        Guest guest = mock(Guest.class);
        Hotel hotel = mock(Hotel.class);

        when(apartment.getStatus()).thenReturn(ApartmentStatus.AVAILABLE);
        when(apartment.getHotel()).thenReturn(hotel);

        Reservation reservation = new Reservation(apartment, guest);

        assertEquals(apartment, reservation.getApartment());
        assertEquals(guest, reservation.getGuest());
        assertEquals(hotel, reservation.getHotel());
    }

    @Test
    void toString_Success() {
        Apartment apartment = mock(Apartment.class);
        Guest guest = mock(Guest.class);

        when(apartment.getStatus()).thenReturn(ApartmentStatus.AVAILABLE);
        when(apartment.getId()).thenReturn(5);
        when(guest.getName()).thenReturn("John Doe");

        Reservation reservation = new Reservation(apartment, guest);
        reservation.setId(10);

        String expectedString = "Reservation ID: 10, Apartment ID: 5, Guest: John Doe";
        assertEquals(expectedString, reservation.toString());
    }
}
