package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Apartment apartment; // Apartment instance used in tests
    private Guest guest; // Guest instance for reservation
    private Reservation reservation; // Reservation instance to test reservation functionality

    @BeforeEach
    void setUp() {
        // Initialize apartment, guest, and reservation instances before each test
        apartment = new Apartment(1, 150.0);
        guest = new Guest("Azer Agazade");
        reservation = new Reservation(apartment, guest);
    }

    @Test
    void testCreateReservation_Success() {
        // Test successful reservation creation
        reservation.createReservation();

        // Verify that the apartment status is set to RESERVED
        assertEquals(ApartmentStatus.RESERVED, apartment.getStatus(), "Apartment should be reserved");
    }

    @Test
    void testCreateReservation_ApartmentAlreadyReservedException() {
        // Set apartment status to RESERVED before creating reservation
        apartment.setStatus(ApartmentStatus.RESERVED);

        // Verify that creating a reservation on an already reserved apartment throws an exception
        assertThrows(ApartmentAlreadyReservedException.class, reservation::createReservation,
                "Should throw ApartmentAlreadyReservedException if apartment is already reserved");
    }

    @Test
    void testCancelReservation_Success() {
        // Create a reservation first, then cancel it
        reservation.createReservation();
        reservation.cancelReservation();

        // Verify that the apartment status is set back to AVAILABLE
        assertEquals(ApartmentStatus.AVAILABLE, apartment.getStatus(), "Apartment should be available after cancellation");
    }

    @Test
    void testCancelReservation_ApartmentNotReservedException() {
        // Verify that canceling a non-reserved apartment throws an exception
        assertThrows(ApartmentNotReservedException.class, reservation::cancelReservation,
                "Should throw ApartmentNotReservedException if apartment is not reserved");
    }
}
