package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;

public class Reservation {
    private Apartment apartment;
    private Guest guest;

    public Reservation(Apartment apartment, Guest guest) {
        this.apartment = apartment;
        this.guest = guest;
    }

    public void createReservation() {
        if (apartment.getStatus() == ApartmentStatus.AVAILABLE) {
            apartment.setStatus(ApartmentStatus.RESERVED);
        } else {
            throw new ApartmentAlreadyReservedException("Apartment is already reserved.");
        }
    }

    public void cancelReservation() {
        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            apartment.setStatus(ApartmentStatus.AVAILABLE);
        } else {
            throw new ApartmentNotReservedException("Apartment is not reserved.");
        }
    }
}
