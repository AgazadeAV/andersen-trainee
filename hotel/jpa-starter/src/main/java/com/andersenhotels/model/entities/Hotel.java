package com.andersenhotels.model.entities;

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
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "hotel")
    private List<Apartment> apartments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "hotel")
    private List<Reservation> reservations = new ArrayList<>();

    public Hotel() {
        // Default constructor for JPA
    }

    @Override
    public String toString() {
        return String.format("Hotel ID: %d", id);
    }
}
