package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.GuestNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Setter
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Reservation() {
        // Default constructor for JPA
    }

    public Reservation(Apartment apartment, Guest guest) {
        if (apartment == null) {
            throw new ApartmentNotFoundException("Apartment cannot be null.");
        }

        if (guest == null) {
            throw new GuestNotFoundException("Guest cannot be null.");
        }

        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            throw new ApartmentAlreadyReservedException("Apartment must have status AVAILABLE to create a reservation.");
        }

        this.apartment = apartment;
        this.guest = guest;
        this.hotel = apartment.getHotel();
    }

    @Override
    public String toString() {
        return String.format(
                "Reservation ID: %d, Apartment ID: %d, Guest: %s, Hotel ID: %d",
                id, apartment.getId(), guest.getName(), hotel.getId());
    }
}
