package com.andersenhotels.presenter;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.Hotel;
import com.andersenhotels.model.exceptions.*;
import com.andersenhotels.model.service.logic.HotelServiceWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Presenter {

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
            log.info("Apartment registered with price: {}", price);
            return true;
        } catch (InvalidPriceException | HotelNotFoundException | IllegalArgumentException e) {
            log.error("Failed to register apartment", e);
            return false;
        }
    }

    public boolean reserveApartment(long id, String guestName) {
        try {
            hotelServiceWrapper.reserveApartment(id, guestName);
            log.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
            return true;
        } catch (ApartmentNotFoundException | InvalidNameException | GuestNotFoundException |
                 ApartmentAlreadyReservedException | IllegalArgumentException e) {
            log.error("Failed to reserve apartment", e);
            return false;
        }
    }

    public boolean releaseApartment(long reservationId) {
        try {
            hotelServiceWrapper.releaseApartment(reservationId);
            log.info("Apartment released: Reservation ID = {}", reservationId);
            return true;
        } catch (ReservationNotFoundException | IllegalArgumentException e) {
            log.error("Failed to release apartment", e);
            return false;
        }
    }

    public List<String> listApartments(int page) {
        try {
            List<Apartment> apartments = hotelServiceWrapper.listApartments(page);
            log.info("Listed apartments for page {}: {} items found.", page, apartments.size());
            return apartments.stream().map(Apartment::toString).toList();
        } catch (WrongPageNumberException e) {
            log.error("Failed to list apartments", e);
            return new ArrayList<>();
        }
    }

    public int getTotalPages() {
        try {
            return hotelServiceWrapper.getTotalPages();
        } catch (HotelNotFoundException e) {
            log.error("Failed to get total pages", e);
            return 0;
        }
    }

    private void initializeHotel() {
        try {
            this.hotel = hotelServiceWrapper.initializeHotel();
            log.info("Hotel successfully initialized. ID: {}", hotel.getId());
        } catch (HotelNotFoundException e) {
            log.error("Failed to initialize hotel", e);
            this.hotel = new Hotel();
        }
    }
}
