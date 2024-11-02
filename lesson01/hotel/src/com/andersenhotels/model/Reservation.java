package com.andersenhotels.model;

import com.andersenhotels.model.exception.ApartmentAlreadyReservedException;
import com.andersenhotels.model.exception.ApartmentNotReservedException;

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
            System.out.println("Apartment " + apartment.getId() + " reserved for " + guest.getName());
        } else {
            throw new ApartmentAlreadyReservedException("Apartment is already reserved.");
        }
    }

    public void cancelReservation() {
        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            apartment.setStatus(ApartmentStatus.AVAILABLE);
            System.out.println("Apartment " + apartment.getId() + " released.");
        } else {
            throw new ApartmentNotReservedException("Apartment is not reserved.");
        }
    }
}
