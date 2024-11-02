package model;

public class Reservation {
    private Apartment apartment;
    private Guest guest;
    private boolean isReserved;

    public Reservation(Apartment apartment, Guest guest) {
        this.apartment = apartment;
        this.guest = guest;
        this.isReserved = false;
    }

    public void createReservation() {
        if (!isReserved) {
            isReserved = true;
            System.out.println("Apartment " + apartment.getId() + " reserved for " + guest.getName());
        } else {
            throw new IllegalStateException("Apartment is already reserved.");
        }
    }

    public void cancelReservation() {
        if (isReserved) {
            isReserved = false;
            System.out.println("Apartment " + apartment.getId() + " released.");
        } else {
            throw new IllegalStateException("Apartment is not reserved.");
        }
    }
}
