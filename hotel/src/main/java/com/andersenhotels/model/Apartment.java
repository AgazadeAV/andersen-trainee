package com.andersenhotels.model;

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
    private int id;

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
            throw new IllegalArgumentException("Hotel cannot be null.");
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.price = price;
        this.hotel = hotel;
        this.status = ApartmentStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format("Apartment ID: %d, Price: %.2f, Status: %s, Hotel ID: %d.", id, price, status, hotel.getId());
    }
}
