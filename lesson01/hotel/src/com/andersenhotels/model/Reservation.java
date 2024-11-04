package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;

/**
 * Manages reservation for an apartment with a guest.
 */
public class Reservation {
    private Apartment apartment;
    private Guest guest;

    /**
     * Initializes reservation with an apartment and guest.
     */
    public Reservation(Apartment apartment, Guest guest) {
        this.apartment = apartment;
        this.guest = guest;
    }

    /**
     * Reserves the apartment if available.
     *
     * @throws ApartmentAlreadyReservedException if the apartment is already reserved.
     */
    public void createReservation() {
        if (apartment.getStatus() == ApartmentStatus.AVAILABLE) {
            apartment.setStatus(ApartmentStatus.RESERVED);
        } else {
            throw new ApartmentAlreadyReservedException("Apartment is already reserved.");
        }
    }

    /**
     * Cancels the reservation if the apartment is reserved.
     *
     * @throws ApartmentNotReservedException if the apartment is not reserved.
     */
    public void cancelReservation() {
        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            apartment.setStatus(ApartmentStatus.AVAILABLE);
        } else {
            throw new ApartmentNotReservedException("Apartment is not reserved.");
        }
    }
}
