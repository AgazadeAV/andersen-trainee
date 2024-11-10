package com.andersenhotels.model;

public class Apartment {
    private int id;
    private double price;
    private ApartmentStatus status;

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
