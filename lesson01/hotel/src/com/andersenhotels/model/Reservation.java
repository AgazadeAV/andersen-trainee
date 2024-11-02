package com.andersenhotels.model;

import com.andersenhotels.model.exception.ApartmentAlreadyReservedException;
import com.andersenhotels.model.exception.ApartmentNotReservedException;

/**
 * Represents a reservation for an apartment by a guest.
 */
public class Reservation {
    private Apartment apartment;
    private Guest guest;

    /**
     * Constructs a new Reservation with the specified apartment and guest.
     *
     * @param apartment the apartment being reserved.
     * @param guest the guest making the reservation.
     */
    public Reservation(Apartment apartment, Guest guest) {
        this.apartment = apartment;
        this.guest = guest;
    }

    /**
     * Creates a reservation for the apartment.
     *
     * @throws ApartmentAlreadyReservedException if the apartment is already reserved.
     */
    public void createReservation() {
        if (apartment.getStatus() == ApartmentStatus.AVAILABLE) {
            apartment.setStatus(ApartmentStatus.RESERVED);
            System.out.println("Apartment " + apartment.getId() + " reserved for " + guest.getName());
        } else {
            throw new ApartmentAlreadyReservedException("Apartment is already reserved.");
        }
    }

    /**
     * Cancels the reservation for the apartment.
     *
     * @throws ApartmentNotReservedException if the apartment is not reserved.
     */
    public void cancelReservation() {
        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            apartment.setStatus(ApartmentStatus.AVAILABLE);
            System.out.println("Apartment " + apartment.getId() + " released.");
        } else {
            throw new ApartmentNotReservedException("Apartment is not reserved.");
        }
    }
}
