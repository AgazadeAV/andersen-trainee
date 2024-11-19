package com.andersenhotels.model.service;

import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.ApartmentStatus;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import com.andersenhotels.presenter.exceptions.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class HotelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HotelService.class);

    private Map<Integer, Apartment> apartments;
    private Map<Integer, Reservation> reservations;
    private int nextApartmentId;

    @JsonIgnore
    private ValueValidator valueValidator;

    public HotelService() {
        this.apartments = new HashMap<>();
        this.reservations = new HashMap<>();
        this.valueValidator = new ValueValidator(this);
        this.nextApartmentId = 1;
        LOGGER.info("HotelService initialized with empty apartments and reservations.");
    }

    public int apartmentsCount() {
        return apartments.size();
    }

    public int reservedApartmentsCount() {
        return reservations.size();
    }

    public int totalPages() {
        return (int) Math.ceil((double) apartments.size() / ConfigManager.getPageSizeForPagination());
    }

    public void registerApartment(double price) {
        if (price < 0) {
            LOGGER.warn("Attempt to register an apartment with a negative price: {}", price);
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }
        Apartment apartment = new Apartment(nextApartmentId++, price);
        apartments.put(apartment.getId(), apartment);
        LOGGER.info("Apartment registered: ID = {}, Price = {}", apartment.getId(), apartment.getPrice());
    }

    public void reserveApartment(int id, String guestName) {
        LOGGER.info("Attempting to reserve apartment with ID {} for guest '{}'", id, guestName);

        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = Optional.ofNullable(apartments.get(id))
                .orElseThrow(() -> {
                    LOGGER.error("Apartment not found for ID {}", id);
                    return new ApartmentNotFoundException(
                            "Apartment not found for the given ID. Please provide ID between 1 and " + apartmentsCount() + ".");
                });

        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation(apartment, guest);
        reservation.createReservation();
        reservations.put(id, reservation);

        LOGGER.info("Apartment reserved: ID = {}, Guest = {}", id, guestName);
    }

    public void releaseApartment(int id) {
        LOGGER.info("Attempting to release apartment with ID {}", id);

        valueValidator.validateApartmentId(id);

        Reservation reservation = Optional.ofNullable(reservations.get(id))
                .orElseThrow(() -> {
                    LOGGER.warn("Attempt to release an apartment that is not reserved: ID {}", id);
                    return new ApartmentNotReservedException("Apartment is not reserved. Please try again.");
                });

        reservation.cancelReservation();
        reservations.remove(id);

        LOGGER.info("Apartment released: ID = {}", id);
    }

    public List<Apartment> listApartments(int page) {
        LOGGER.info("Listing apartments for page {}", page);

        if (totalPages() == 0) {
            LOGGER.warn("No apartments were registered.");
            throw new ApartmentNotFoundException("No apartments were registered.");
        }

        if (page <= 0 || ConfigManager.getPageSizeForPagination() <= 0) {
            LOGGER.error("Invalid page number or page size: page = {}, page size = {}",
                    page, ConfigManager.getPageSizeForPagination());
            throw new ApartmentNotFoundException("Page number and page size must be greater than 0.");
        }

        if (page > totalPages()) {
            LOGGER.warn("Requested page {} exceeds total pages {}", page, totalPages());
            throw new ApartmentNotFoundException("No apartments found for the requested page number. " +
                    "Valid page numbers are from 1 to " + totalPages() + ".");
        }

        List<Apartment> result = apartments.values().stream()
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

        Apartment apartment = Optional.ofNullable(apartments.get(apartmentId))
                .orElseThrow(() -> {
                    LOGGER.error("Apartment not found for ID {}", apartmentId);
                    return new ApartmentNotFoundException("Apartment not found for the given ID.");
                });

        apartment.setStatus(newStatus);
        LOGGER.info("Apartment status changed: ID = {}, New Status = {}", apartmentId, newStatus);
    }
}
