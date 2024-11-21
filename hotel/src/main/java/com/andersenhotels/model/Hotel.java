package com.andersenhotels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Setter
@Getter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private List<Apartment> apartments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private List<Reservation> reservations;

    public Hotel() {
        this.apartments = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public Hotel(List<Apartment> apartments, List<Reservation> reservations) {
        this.apartments = apartments != null ? apartments : new ArrayList<>();
        this.reservations = reservations != null ? reservations : new ArrayList<>();
    }
}
