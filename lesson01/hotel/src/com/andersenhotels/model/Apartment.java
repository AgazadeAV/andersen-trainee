package com.andersenhotels.model;

/**
 * Represents an apartment with ID, price, and status.
 */
public class Apartment {
    private int id;
    private double price;
    private ApartmentStatus status;

    /**
     * Constructs an apartment with ID and price, initially AVAILABLE.
     */
    public Apartment(int id, double price) {
        this.id = id;
        this.price = price;
        this.status = ApartmentStatus.AVAILABLE;
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

    /**
     * Returns a string with apartment ID, price, and status.
     */
    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price + ", Status: " + status + ".";
    }
}
