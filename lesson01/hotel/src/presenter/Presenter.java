package presenter;

import model.Hotel;
import view.View;

public class Presenter {
    private View view;
    private Hotel hotel;

    public Presenter(View view) {
        this.view = view;
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
