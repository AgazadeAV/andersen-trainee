package model;

public class Apartment {
    private int id;
    private double price;

    public Apartment(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price;
    }
}
