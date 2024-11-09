package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.service.StateManager;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.view.common.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Presenter {
    private HotelService hotelService;
    private View view;

    public Presenter(View view) {
        this.hotelService = new HotelService();
        this.view = view;
    }

    public Presenter(View view, HotelService hotelService) {
        this.hotelService = hotelService;
        this.view = view;
    }

    public boolean registerApartment(double price) {
        try {
            hotelService.registerApartment(price);
            return true;
        } catch (InvalidPriceException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public boolean reserveApartment(int id, String guestName) {
        try {
            hotelService.reserveApartment(id, guestName);
            return true;
        } catch (ApartmentNotFoundException | ApartmentAlreadyReservedException | InvalidNameException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public boolean releaseApartment(int id) {
        try {
            hotelService.releaseApartment(id);
            return true;
        } catch (ApartmentNotFoundException | ApartmentNotReservedException e) {
            view.displayError(e.getMessage());
            return false;
        }
    }

    public List<String> listApartments(int page) {
        try {
            return hotelService.listApartments(page).stream()
                    .map(Apartment::toString)
                    .toList();
        } catch (ApartmentNotFoundException e) {
            view.displayError(e.getMessage());
            return new ArrayList<>();
        }
    }

    public int getTotalPages() {
        return hotelService.getTotalPages();
    }

    public boolean saveState(String filePath) {
        try {
            StateManager.saveState(hotelService, filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadState(String filePath) {
        try {
            this.hotelService = StateManager.loadState(filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
