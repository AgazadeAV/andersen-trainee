package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Apartment apartment;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        Guest guest = new Guest("Azer Agazade");
        apartment = new Apartment(1, 150.0);
        reservation = new Reservation(apartment, guest);
    }

    @Test
    void createReservation_Success() {
        reservation.createReservation();

        assertEquals(ApartmentStatus.RESERVED, apartment.getStatus(), "Apartment should be reserved");
    }

    @Test
    void createReservation_ApartmentAlreadyReservedException() {
        apartment.setStatus(ApartmentStatus.RESERVED);

        assertThrows(ApartmentAlreadyReservedException.class, reservation::createReservation,
                "Should throw ApartmentAlreadyReservedException if apartment is already reserved");
    }

    @Test
    void cancelReservation_Success() {
        reservation.createReservation();
        reservation.cancelReservation();

        assertEquals(ApartmentStatus.AVAILABLE, apartment.getStatus(),
                "Apartment should be available after cancellation");
    }

    @Test
    void cancelReservation_ApartmentNotReservedException() {
        assertThrows(ApartmentNotReservedException.class, reservation::cancelReservation,
                "Should throw ApartmentNotReservedException if apartment is not reserved");
    }
}
