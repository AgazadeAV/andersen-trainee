package com.andersenhotels.presenter;

import com.andersenhotels.model.service.HotelService;

public class Presenter {
    private HotelService hotelService;

    public Presenter() {
        this.hotelService = new HotelService();
    }

    public int getApartmentsCount() {
        return hotelService.getApartmentsCount();
    }

    public void registerApartment(double price) {
        hotelService.registerApartment(price);
    }

    public void reserveApartment(int id, String clientName) {
        hotelService.reserveApartment(id, clientName);
    }

    public void releaseApartment(int id) {
        hotelService.releaseApartment(id);
    }

    public void listApartments(int page, int pageSize) {
        hotelService.listApartments(page, pageSize);
    }
}
