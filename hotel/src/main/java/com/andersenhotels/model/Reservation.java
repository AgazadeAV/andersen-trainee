package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;

/**
 * Represents a reservation for an apartment by a guest, managing the reservation state
 * of the apartment. Ensures that apartments cannot be reserved if already occupied
 * and can only be released if currently reserved.
 */
public class Reservation {
    private Apartment apartment;
    private Guest guest;

    /**
     * Initializes a new Reservation instance with the specified apartment and guest.
     *
     * @param apartment The apartment to be reserved. Must not be null and should be managed
     *                  within the context of the hotel.
     * @param guest     The guest making the reservation. Should contain valid guest details.
     */
    public Reservation(Apartment apartment, Guest guest) {
        this.apartment = apartment;
        this.guest = guest;
    }

    /**
     * Creates a reservation by marking the apartment as reserved if it is currently available.
     * Updates the apartment's status to RESERVED.
     *
     * @throws ApartmentAlreadyReservedException if the apartment is already reserved,
     *                                           preventing double-booking.
     */
    public void createReservation() {
        if (apartment.getStatus() == ApartmentStatus.AVAILABLE) {
            apartment.setStatus(ApartmentStatus.RESERVED);
        } else {
            throw new ApartmentAlreadyReservedException("Apartment is already reserved by " + guest.getName() + ".");
        }
    }

    /**
     * Cancels the reservation by marking the apartment as available if it is currently reserved.
     * Updates the apartment's status to AVAILABLE.
     *
     * @throws ApartmentNotReservedException if the apartment is not reserved,
     *                                       indicating no reservation to cancel.
     */
    public void cancelReservation() {
        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            apartment.setStatus(ApartmentStatus.AVAILABLE);
        } else {
            throw new ApartmentNotReservedException("Apartment is not reserved.");
        }
    }
}
