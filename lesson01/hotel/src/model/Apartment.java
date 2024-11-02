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

    public void reserve(String clientName) {
        if (!isReserved) {
            this.isReserved = true;
            this.clientName = clientName;
        } else {
            throw new IllegalStateException("Apartment is already reserved.");
        }
    }

    public void release() {
        if (isReserved) {
            this.isReserved = false;
            this.clientName = null;
        } else {
            throw new IllegalStateException("Apartment is not reserved.");
        }
    }

    @Override
    public String toString() {
        return "Apartment ID: " + id + ", Price: " + price +
                ", Reserved: " + isReserved + (isReserved ? ", Client: " + clientName : "");
    }
}
