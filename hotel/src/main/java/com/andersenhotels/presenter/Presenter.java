package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.storage.*;
import com.andersenhotels.model.storage.DataStorageFactory;
import com.andersenhotels.model.storage.DataStorageType;
import com.andersenhotels.model.storage.db_storage.LiquibaseRunner;
import com.andersenhotels.model.storage.json_storage.JsonStorage;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.view.common.View;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Presenter {

    private HotelService hotelService;
    private final View view;
    private DataStorage currentStorage;

    public Presenter(View view) {
        this.hotelService = new HotelService();
        this.view = view;
        this.currentStorage = DataStorageFactory.getStorage(DataStorageType.JSON);
    }

    public Presenter(View view, HotelService hotelService) {
        this.hotelService = hotelService;
        this.view = view;
    }

    public void setStorageType(int choice) {
        if (choice == 1) {
            currentStorage = DataStorageFactory.getStorage(DataStorageType.JSON);
            view.displayMessage("Storage type switched to JSON.");
        } else if (choice == 2) {
            currentStorage = DataStorageFactory.getStorage(DataStorageType.DATABASE);
            view.displayMessage("Storage type switched to Database.");

            LiquibaseRunner.runLiquibaseMigrations();
            view.displayMessage("Database migrations applied successfully.");
        } else {
            view.displayError("Invalid storage type.");
        }
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
            currentStorage.saveState(hotelService);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadState() {
        try {
            this.hotelService = currentStorage.loadState();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean saveStateForTests() {
        try {
            currentStorage.saveStateForTests(hotelService);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadStateForTests() {
        try {
            this.hotelService = currentStorage.loadStateForTests();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getTEST_PATH() {
        return JsonStorage.getTEST_PATH();
    }
}
