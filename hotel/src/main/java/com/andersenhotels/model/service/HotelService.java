package com.andersenhotels.model.service;

import com.andersenhotels.model.*;
import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;
import com.andersenhotels.presenter.exceptions.InvalidPriceException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    @Getter
    private final Hotel hotel;
    private final ValueValidator valueValidator;

    public HotelService(Hotel hotel) {
        this.hotel = hotel;
        this.valueValidator = new ValueValidator(this);
    }

    public boolean doesHaveApartment(int id) {
        return hotel.getApartments().containsKey(id);
    }

    public int getApartmentsCount() {
        return hotel.getApartments().size();
    }

    public int getReservedApartmentsCount() {
        return hotel.getReservations().size();
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) getApartmentsCount() / ConfigManager.getPageSizeForPagination());
    }

    public void registerApartment(double price) {
        if (price < 0) {
            LOGGER.warn("Attempt to register an apartment with a negative price: {}", price);
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }

        Apartment apartment = new Apartment(hotel.getNextApartmentId(), price);
        hotel.setNextApartmentId(hotel.getNextApartmentId() + 1);

        try {
            hotel.addApartment(apartment.getId(), apartment);
            LOGGER.info("Apartment registered: ID = {}, Price = {}", apartment.getId(), apartment.getPrice());
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to register apartment: {}", e.getMessage());
        }
    }

    public void reserveApartment(int id, String guestName) {
        LOGGER.info("Attempting to reserve apartment with ID {} for guest '{}'", id, guestName);

        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = Optional.ofNullable(hotel.getApartments().get(id))
                .orElseThrow(() -> {
                    System.out.println(id + " - " + guestName);
                    LOGGER.error("Apartment not found for ID {}", id);
                    return new ApartmentNotFoundException(
                            "Apartment not found for the given ID. " +
                                    "Please provide ID between 1 and " + getApartmentsCount() + ".");
                });

        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation(apartment, guest);
        reservation.createReservation();

        try {
            hotel.addReservation(id, reservation);
            LOGGER.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to reserve apartment: {}", e.getMessage());
        }
    }

    public void releaseApartment(int id) {
        LOGGER.info("Attempting to release apartment with ID {}", id);

        valueValidator.validateApartmentId(id);

        Reservation reservation = Optional.ofNullable(hotel.getReservations().get(id))
                .orElseThrow(() -> {
                    LOGGER.warn("Attempt to release an apartment that is not reserved: ID {}", id);
                    return new ApartmentNotReservedException("Apartment is not reserved. Please try again.");
                });

        reservation.cancelReservation();

        try {
            hotel.removeReservation(id);
            LOGGER.info("Apartment released: ID = {}", id);
        } catch (IllegalStateException e) {
            LOGGER.error("Failed to release apartment: {}", e.getMessage());
        }
    }

    public List<Apartment> listApartments(int page) {
        LOGGER.info("Listing apartments for page {}", page);

        if (getTotalPages() == 0) {
            LOGGER.warn("No apartments were registered.");
            throw new ApartmentNotFoundException("No apartments were registered.");
        }

        if (page <= 0 || ConfigManager.getPageSizeForPagination() <= 0) {
            LOGGER.error("Invalid page number or page size: page = {}, page size = {}",
                    page, ConfigManager.getPageSizeForPagination());
            throw new ApartmentNotFoundException("Page number and page size must be greater than 0.");
        }

        if (page > getTotalPages()) {
            LOGGER.warn("Requested page {} exceeds total pages {}", page, getTotalPages());
            throw new ApartmentNotFoundException("No apartments found for the requested page number. " +
                    "Valid page numbers are from 1 to " + getTotalPages() + ".");
        }

        List<Apartment> result = hotel.getApartments().values().stream()
                .sorted(Comparator.comparingInt(Apartment::getId))
                .skip((long) (page - 1) * ConfigManager.getPageSizeForPagination())
                .limit(ConfigManager.getPageSizeForPagination())
                .toList();

        LOGGER.info("Found {} apartments for page {}", result.size(), page);
        return result;
    }

    public void changeApartmentStatus(int apartmentId, ApartmentStatus newStatus) {
        LOGGER.info("Changing status of apartment ID {} to {}", apartmentId, newStatus);

        if (!ConfigManager.isAllowApartmentStatusChange()) {
            LOGGER.error("Attempt to change apartment status is disabled by configuration.");
            throw new UnsupportedOperationException("Changing apartment status is disabled by configuration.");
        }

        Apartment apartment = Optional.ofNullable(hotel.getApartments().get(apartmentId))
                .orElseThrow(() -> {
                    LOGGER.error("Apartment not found for ID {}", apartmentId);
                    return new ApartmentNotFoundException("Apartment not found for the given ID.");
                });

        apartment.setStatus(newStatus);
        LOGGER.info("Apartment status changed: ID = {}, New Status = {}", apartmentId, newStatus);
    }
}
