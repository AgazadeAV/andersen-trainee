package com.andersenhotels.model;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Setter
@Getter
public class Hotel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hotel.class);

    private Map<Integer, Apartment> apartments;
    private Map<Integer, Reservation> reservations;
    private int nextApartmentId;

    public Hotel() {
        this.apartments = new HashMap<>();
        this.reservations = new HashMap<>();
        this.nextApartmentId = 1;
        LOGGER.info("Hotel initialized: nextApartmentId = {}", nextApartmentId);
    }

    public void addApartment(int id, Apartment apartment) {
        LOGGER.info("Attempting to add apartment with ID = {}", id);
        if (apartments.containsKey(id)) {
            String message = "Hotel already has apartment with ID = " + id;
            LOGGER.error(message);
            throw new IllegalStateException(message);
        }
        apartments.put(id, apartment);
        LOGGER.info("Apartment added successfully: ID = {}, Details = {}", id, apartment);
    }

    public void addReservation(int id, Reservation reservation) {
        LOGGER.info("Attempting to add reservation for Apartment ID = {}", id);
        if (reservations.containsKey(id)) {
            String message = "Reservation for apartment ID " + id + " already exists.";
            LOGGER.error(message);
            throw new IllegalStateException(message);
        }
        reservations.put(id, reservation);
        LOGGER.info("Reservation added successfully: Apartment ID = {}, Guest = {}", id, reservation.getGuest().getName());
    }

    public void removeReservation(int id) {
        LOGGER.info("Attempting to remove reservation for Apartment ID = {}", id);
        if (!reservations.containsKey(id)) {
            String message = "Reservation for apartment ID " + id + " does not exist.";
            LOGGER.error(message);
            throw new IllegalStateException(message);
        }
        reservations.remove(id);
        LOGGER.info("Reservation removed successfully for Apartment ID = {}", id);
    }
}
