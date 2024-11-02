package model;

public class Apartment {
    private int id;
    private double price;
    private boolean isReserved;
    private String clientName;

    public Apartment(int id, double price) {
        this.id = id;
        this.price = price;
        this.isReserved = false;
        this.clientName = null;
    }

    public int getId() {
        return id;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void reserve(String clientName) {
        this.isReserved = true;
        this.clientName = clientName;
    }

    public void release() {
        this.isReserved = false;
        this.clientName = null;
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price +
                ", Reserved: " + isReserved + (isReserved ? ", Client: " + clientName : "");
    }
}
