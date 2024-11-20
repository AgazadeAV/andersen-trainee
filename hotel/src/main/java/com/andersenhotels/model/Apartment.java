package com.andersenhotels.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public Apartment() {
        this.status = ApartmentStatus.AVAILABLE;
    }

    @JsonCreator
    public Apartment(@JsonProperty("id") int id, @JsonProperty("price") double price) {
        this.id = id;
        this.price = price;
        this.status = ApartmentStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price + ", Status: " + status + ".";
    }
}
