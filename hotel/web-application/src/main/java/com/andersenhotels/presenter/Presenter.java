package com.andersenhotels.presenter;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.Hotel;
import com.andersenhotels.model.exceptions.*;
import com.andersenhotels.model.service.logic.HotelServiceWrapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Presenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Presenter.class);

    private final HotelServiceWrapper hotelServiceWrapper;

    @Getter
    private Hotel hotel;

    @Value("${pagination.page-size}")
    private int pageSize;

    public Presenter(HotelServiceWrapper hotelServiceWrapper) {
        this.hotelServiceWrapper = hotelServiceWrapper;
        initializeHotel();
    }

    public boolean registerApartment(double price) {
        try {
            hotelServiceWrapper.registerApartment(price, hotel.getId());
            LOGGER.info("Apartment registered with price: {}", price);
            return true;
        } catch (InvalidPriceException | HotelNotFoundException | IllegalArgumentException e) {
            LOGGER.error("Failed to register apartment", e);
            return false;
        }
    }

    public boolean reserveApartment(long id, String guestName) {
        try {
            hotelServiceWrapper.reserveApartment(id, guestName);
            LOGGER.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
            return true;
        } catch (ApartmentNotFoundException | InvalidNameException | GuestNotFoundException |
                 ApartmentAlreadyReservedException | IllegalArgumentException e) {
            LOGGER.error("Failed to reserve apartment", e);
            return false;
        }
    }

    public boolean releaseApartment(long reservationId) {
        try {
            hotelServiceWrapper.releaseApartment(reservationId);
            LOGGER.info("Apartment released: Reservation ID = {}", reservationId);
            return true;
        } catch (ReservationNotFoundException | IllegalArgumentException e) {
            LOGGER.error("Failed to release apartment", e);
            return false;
        }
    }

    public List<String> listApartments(int page) {
        try {
            List<Apartment> apartments = hotelServiceWrapper.listApartments(page);
            LOGGER.info("Listed apartments for page {}: {} items found.", page, apartments.size());
            return apartments.stream().map(Apartment::toString).toList();
        } catch (WrongPageNumberException e) {
            LOGGER.error("Failed to list apartments", e);
            return new ArrayList<>();
        }
    }

    public int getTotalPages() {
        try {
            return hotelServiceWrapper.getTotalPages();
        } catch (HotelNotFoundException e) {
            LOGGER.error("Failed to get total pages", e);
            return 0;
        }
    }

    private void initializeHotel() {
        try {
            this.hotel = hotelServiceWrapper.initializeHotel();
            LOGGER.info("Hotel successfully initialized. ID: {}", hotel.getId());
        } catch (HotelNotFoundException e) {
            LOGGER.error("Failed to initialize hotel", e);
            this.hotel = new Hotel();
        }
    }
}
