package com.andersenhotels.model.entities;

import com.andersenhotels.model.exceptions.HotelNotFoundException;
import com.andersenhotels.model.exceptions.InvalidPriceException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "apartments")
@Setter
@Getter
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApartmentStatus status;

    @OneToOne(mappedBy = "apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Apartment() {
        // Default constructor for JPA
    }

    public Apartment(double price, Hotel hotel) {
        if (hotel == null) {
            throw new HotelNotFoundException("Hotel cannot be null.");
        }

        if (price < 0) {
            throw new InvalidPriceException("Price cannot be negative.");
        }

        this.price = price;
        this.hotel = hotel;
        this.status = ApartmentStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("Apartment ID: %d, Price: %.2f, Status: %s, Hotel ID: %d.",
                id, price, status, hotel.getId());
    }
}
