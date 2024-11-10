package com.andersenhotels.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Apartment {

    private int id;
    private double price;
    private ApartmentStatus status;

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
