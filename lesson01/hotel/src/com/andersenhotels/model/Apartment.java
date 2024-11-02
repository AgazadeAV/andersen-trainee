package com.andersenhotels.model;

public class Apartment {
    private int id;
    private double price;
    private ApartmentStatus status;

    public Apartment(int id, double price) {
        this.id = id;
        this.price = price;
        this.status = ApartmentStatus.AVAILABLE; // Изначально квартира доступна
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price + ", Status: " + status;
    }
}
