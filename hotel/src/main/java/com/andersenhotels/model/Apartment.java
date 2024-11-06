package com.andersenhotels.model;

/**
 * Represents an apartment in the hotel, with a unique ID, rental price, and availability status.
 * Each apartment is identified by an integer ID, holds a price indicating its rental cost,
 * and has a status that denotes whether it is available or reserved.
 */
public class Apartment {
    private int id;
    private double price;
    private ApartmentStatus status;

    /**
     * Constructs a new Apartment instance with a specified ID and price.
     * By default, each new apartment is set to be AVAILABLE.
     *
     * @param id    The unique identifier for this apartment. Expected to be assigned sequentially.
     * @param price The rental price for this apartment. Must be a positive number.
     */
    public Apartment(int id, double price) {
        this.id = id;
        this.price = price;
        this.status = ApartmentStatus.AVAILABLE; // New apartments are available by default
    }

    public int getId() {
        return id;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price + ", Status: " + status + ".";
    }
}
