package com.andersenhotels.model;

/**
 * Represents an apartment in the hotel.
 */
public class Apartment {
    private int id;
    private double price;
    private ApartmentStatus status;

    /**
     * Constructs a new Apartment with the specified ID and price.
     *
     * @param id the unique identifier for the apartment.
     * @param price the price of the apartment.
     */
    public Apartment(int id, double price) {
        this.id = id;
        this.price = price;
        this.status = ApartmentStatus.AVAILABLE; // Initially, the apartment is available
    }

    /**
     * Gets the ID of the apartment.
     *
     * @return the ID of the apartment.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the current status of the apartment.
     *
     * @return the status of the apartment.
     */
    public ApartmentStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the apartment.
     *
     * @param status the new status of the apartment.
     */
    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price + ", Status: " + status;
    }
}
