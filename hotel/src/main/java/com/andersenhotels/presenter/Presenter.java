package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.storage.DataStorage;
import com.andersenhotels.model.storage.DataStorageFactory;
import com.andersenhotels.model.storage.DataStorageType;
import com.andersenhotels.model.storage.db_storage.LiquibaseRunner;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Presenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Presenter.class);

    private final View view;

    private Hotel hotel;
    private HotelService hotelService;
    private DataStorage currentStorage;

    public Presenter(View view) {
        this.view = view;
        this.hotel = new Hotel();
        this.hotelService = new HotelService(hotel);
        this.currentStorage = DataStorageFactory.getStorage(DataStorageType.JSON);
        LOGGER.info("Presenter initialized with default JSON storage.");
    }

    public Presenter(View view, HotelService hotelService) {
        this.view = view;
        this.hotel = hotelService.getHotel();
        this.hotelService = hotelService;
        this.currentStorage = DataStorageFactory.getStorage(DataStorageType.JSON);
        LOGGER.info("Presenter initialized with provided HotelService.");
    }

    public void setStorageType(int choice) {
        if (choice == 1) {
            currentStorage = DataStorageFactory.getStorage(DataStorageType.JSON);
            view.displayMessage("Storage type switched to JSON.");
            LOGGER.info("Storage type switched to JSON.");
        } else if (choice == 2) {
            currentStorage = DataStorageFactory.getStorage(DataStorageType.DATABASE);
            view.displayMessage("Storage type switched to Database.");
            LOGGER.info("Storage type switched to Database.");

            try {
                LiquibaseRunner.runLiquibaseMigrations();
                view.displayMessage("Database migrations applied successfully.");
                LOGGER.info("Liquibase migrations applied successfully.");
            } catch (RuntimeException e) {
                view.displayError("Error applying database migrations: " + e.getMessage());
                LOGGER.error("Error applying database migrations", e);
            }
        } else {
            view.displayError("Invalid storage type.");
            LOGGER.warn("Invalid storage type choice: {}", choice);
        }
    }

    public boolean registerApartment(double price) {
        try {
            hotelService.registerApartment(price);
            LOGGER.info("Apartment registered with price: {}", price);
            return true;
        } catch (InvalidPriceException e) {
            view.displayError(e.getMessage());
            LOGGER.warn("Failed to register apartment with price {}: {}", price, e.getMessage());
            return false;
        }
    }

    public boolean reserveApartment(int id, String guestName) {
        try {
            hotelService.reserveApartment(id, guestName);
            LOGGER.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
            return true;
        } catch (ApartmentNotFoundException | ApartmentAlreadyReservedException | InvalidNameException e) {
            view.displayError(e.getMessage());
            LOGGER.warn("Failed to reserve apartment ID {} for guest '{}': {}", id, guestName, e.getMessage());
            return false;
        }
    }

    public boolean releaseApartment(int id) {
        try {
            hotelService.releaseApartment(id);
            LOGGER.info("Apartment released: ID = {}", id);
            return true;
        } catch (ApartmentNotFoundException | ApartmentNotReservedException e) {
            view.displayError(e.getMessage());
            LOGGER.warn("Failed to release apartment ID {}: {}", id, e.getMessage());
            return false;
        }
    }

    public List<String> listApartments(int page) {
        try {
            List<String> apartments = hotelService.listApartments(page).stream()
                    .map(Apartment::toString)
                    .toList();
            LOGGER.info("Listed apartments for page {}: {} items found.", page, apartments.size());
            return apartments;
        } catch (ApartmentNotFoundException e) {
            view.displayError(e.getMessage());
            LOGGER.warn("Failed to list apartments for page {}: {}", page, e.getMessage());
            return new ArrayList<>();
        }
    }

    public int getTotalPages() {
        int totalPages = hotelService.getTotalPages();
        LOGGER.debug("Total pages calculated: {}", totalPages);
        return totalPages;
    }

    public boolean saveState() {
        try {
            currentStorage.saveState(hotel);
            LOGGER.info("Application state saved successfully.");
            return true;
        } catch (IOException e) {
            view.displayError("Failed to save application state.");
            LOGGER.error("Failed to save application state", e);
            return false;
        }
    }

    public boolean loadState() {
        try {
            this.hotel = currentStorage.loadState();
            this.hotelService = new HotelService(hotel);
            LOGGER.info("Application state loaded successfully.");
            return true;
        } catch (Exception e) {
            view.displayError("Failed to load application state.");
            LOGGER.error("Failed to load application state", e);
            return false;
        }
    }

    public boolean saveStateForTests() {
        try {
            currentStorage.saveStateForTests(hotel);
            LOGGER.info("Test state saved successfully.");
            return true;
        } catch (IOException e) {
            view.displayError("Failed to save test state.");
            LOGGER.error("Failed to save test state", e);
            return false;
        }
    }

    public boolean loadStateForTests() {
        try {
            this.hotel = currentStorage.loadStateForTests();
            this.hotelService = new HotelService(hotel);
            LOGGER.info("Test state loaded successfully.");
            return true;
        } catch (IOException e) {
            view.displayError("Failed to load test state.");
            LOGGER.error("Failed to load test state", e);
            return false;
        }
    }
}
