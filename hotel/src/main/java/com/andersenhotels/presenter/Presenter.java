package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.storage.DatabaseStorage;
import com.andersenhotels.model.storage.LiquibaseRunner;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Presenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Presenter.class);

    private final View view;
    private final DatabaseStorage databaseStorage;
    private Hotel hotel;
    private HotelService hotelService;

    public Presenter(View view) {
        this.view = view;
        this.databaseStorage = new DatabaseStorage();

        try {
            LiquibaseRunner.runLiquibaseMigrations();
            LOGGER.info("Database migrations applied successfully.");
        } catch (RuntimeException e) {
            LOGGER.error("Failed to apply database migrations: {}", e.getMessage());
            view.displayError("Error applying database migrations: " + e.getMessage());
        }

        this.hotel = databaseStorage.loadState();
        this.hotelService = new HotelService(hotel);

        LOGGER.info("Presenter initialized with database storage.");
    }

    public boolean registerApartment(double price) {
        try {
            hotelService.registerApartment(price);
            saveState();
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
            saveState();
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
            saveState();
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
            databaseStorage.saveState(hotel);
            LOGGER.info("Hotel state saved to the database successfully.");
            return true;
        } catch (Exception e) {
            view.displayError("Failed to save hotel state to the database.");
            LOGGER.error("Failed to save hotel state to the database", e);
            return false;
        }
    }

    public boolean loadState() {
        try {
            this.hotel = databaseStorage.loadState();
            this.hotelService = new HotelService(hotel);
            LOGGER.info("Hotel state loaded from the database successfully.");
            return true;
        } catch (Exception e) {
            view.displayError("Failed to load hotel state from the database.");
            LOGGER.error("Failed to load hotel state from the database", e);
            return false;
        }
    }

    public boolean saveStateForTests() {
        return saveState();
    }

    public boolean loadStateForTests() {
        return loadState();
    }
}
