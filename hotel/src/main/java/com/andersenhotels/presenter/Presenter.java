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
    private final View view;

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
        return hotelService.totalPages();
    }

    public boolean saveState() {
        try {
            StateManager.saveState(hotelService);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadState() {
        try {
            this.hotelService = StateManager.loadState();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveStateForTests(String testPath) {
        try {
            StateManager.saveStateForTests(hotelService, testPath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadStateForTests(String testPath) {
        try {
            this.hotelService = StateManager.loadStateForTests(testPath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
