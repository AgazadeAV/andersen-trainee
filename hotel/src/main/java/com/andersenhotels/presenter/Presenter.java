package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.service.logic.HotelServiceWrapper;
import com.andersenhotels.presenter.exceptions.*;
import com.andersenhotels.view.View;
import jakarta.persistence.PersistenceException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Presenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Presenter.class);

    private final View view;
    private final HotelServiceWrapper hotelServiceWrapper;

    @Getter
    private Hotel hotel;

    public Presenter(View view) {
        this.view = view;
        this.hotelServiceWrapper = new HotelServiceWrapper();
        initializeHotel();
    }

    public boolean registerApartment(double price) {
        try {
            hotelServiceWrapper.registerApartment(price, hotel);
            LOGGER.info("Apartment registered with price: {}", price);
            return true;
        } catch (InvalidPriceException |
                 HotelNotFoundException |
                 IllegalArgumentException |
                 PersistenceException e) {
            handleException("Failed to register apartment", e);
            return false;
        }
    }

    public boolean reserveApartment(int id, String guestName) {
        try {
            hotelServiceWrapper.reserveApartment(hotel, id, guestName);
            LOGGER.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
            return true;
        } catch (ApartmentNotFoundException |
                 InvalidNameException |
                 GuestNotFoundException |
                 ApartmentAlreadyReservedException |
                 IllegalArgumentException |
                 PersistenceException e) {
            handleException("Failed to reserve apartment", e);
            return false;
        }
    }

    public boolean releaseApartment(int reservationId) {
        try {
            hotelServiceWrapper.releaseApartment(hotel, reservationId);
            LOGGER.info("Apartment released: Reservation ID = {}", reservationId);
            return true;
        } catch (ReservationNotFoundException |
                 IllegalArgumentException |
                 PersistenceException e) {
            handleException("Failed to release apartment", e);
            return false;
        }
    }

    public List<String> listApartments(int page) {
        try {
            int pageSize = ConfigManager.getPageSizeForPagination();
            List<Apartment> apartments = hotelServiceWrapper.listApartments(page, pageSize);

            LOGGER.info("Listed apartments for page {}: {} items found.", page, apartments.size());
            return apartments.stream().map(Apartment::toString).toList();
        } catch (PersistenceException | WrongPageNumberException e) {
            handleException("Failed to list apartments", e);
            return new ArrayList<>();
        }
    }

    public int getTotalPages() {
        try {
            return hotelServiceWrapper.getTotalPages();
        } catch (PersistenceException e) {
            handleException("Failed to get total pages", e);
            return 0;
        }
    }

    private void initializeHotel() {
        try {
            this.hotel = hotelServiceWrapper.initializeHotel();
            LOGGER.info("Hotel successfully initialized. ID: {}", hotel.getId());
        } catch (IllegalArgumentException | PersistenceException e) {
            handleException("Failed to initialize hotel", e);
            this.hotel = new Hotel();
        }
    }

    private void handleException(String message, Exception e) {
        view.displayError(message + ": " + e.getMessage());
        LOGGER.error("{}: {}", message, e.getMessage(), e);
    }
}
