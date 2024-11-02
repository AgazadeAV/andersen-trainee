package presenter;

import model.Hotel;

public class Presenter {
    private Hotel hotel;

    public Presenter() {
        this.hotel = new Hotel();
    }

    public int getApartmentsCount() {
        return hotel.getApartmentsCount();
    }

    public void registerApartment(double price) {
        hotel.registerApartment(price);
    }

    public void reserveApartment(int id, String clientName) {
        hotel.reserveApartment(id, clientName);
    }

    public void releaseApartment(int id) {
        hotel.releaseApartment(id);
    }

    public void listApartments(int page, int pageSize) {
        hotel.listApartments(page, pageSize);
    }
}
