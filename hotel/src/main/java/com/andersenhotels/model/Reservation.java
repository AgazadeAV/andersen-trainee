package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "reservations")
@Setter
@Getter
public class Reservation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reservation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    public Reservation() {
    }

    public Reservation(Apartment apartment, Guest guest) {
        if (apartment == null || guest == null) {
            throw new IllegalArgumentException("Apartment and Guest cannot be null.");
        }
        this.apartment = apartment;
        this.guest = guest;
    }
}
