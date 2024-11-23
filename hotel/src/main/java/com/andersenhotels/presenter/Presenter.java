package com.andersenhotels.presenter;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Hotel;
import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.service.logic.HotelServiceWrapper;
import com.andersenhotels.view.View;
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
        } catch (Exception e) {
            handleException("Failed to register apartment", e);
            return false;
        }
    }

    public boolean reserveApartment(int id, String guestName) {
        try {
            hotelServiceWrapper.reserveApartment(hotel, id, guestName);
            LOGGER.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
            return true;
        } catch (Exception e) {
            handleException("Failed to reserve apartment", e);
            return false;
        }
    }

    public boolean releaseApartment(int reservationId) {
        try {
            hotelServiceWrapper.releaseApartment(hotel, reservationId);
            LOGGER.info("Apartment released: Reservation ID = {}", reservationId);
            return true;
        } catch (Exception e) {
            handleException("Failed to release apartment", e);
            return false;
        }
    }

    public List<String> listApartments(int page) {
        try {
            int totalPages = getTotalPages();
            if (page <= 0 || page > totalPages) {
                throw new IllegalArgumentException("Invalid page number.");
            }

            int pageSize = ConfigManager.getPageSizeForPagination();
            List<Apartment> apartments = hotelServiceWrapper.listApartments(page, pageSize);

            LOGGER.info("Listed apartments for page {}: {} items found.", page, apartments.size());
            return apartments.stream().map(Apartment::toString).toList();
        } catch (Exception e) {
            handleException("Failed to list apartments", e);
            return new ArrayList<>();
        }
    }

    public int getTotalPages() {
        int apartmentsCount = hotelServiceWrapper.getApartmentsCount();
        int pageSize = ConfigManager.getPageSizeForPagination();
        return (int) Math.ceil((double) apartmentsCount / pageSize);
    }

    private void initializeHotel() {
        try {
            this.hotel = hotelServiceWrapper.initializeHotel();
            LOGGER.info("Hotel successfully initialized. ID: {}", hotel.getId());
        } catch (Exception e) {
            handleException("Failed to initialize hotel", e);
            this.hotel = new Hotel();
        }
    }

    private void handleException(String message, Exception e) {
        view.displayError(message + ": " + e.getMessage());
        LOGGER.error("{}: {}", message, e.getMessage(), e);
    }
}
