package com.andersenhotels.model;

import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotReservedException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
@Getter
public class Reservation {

    private static final Logger LOGGER = LoggerFactory.getLogger(Reservation.class);

    private Apartment apartment;
    private Guest guest;

    @JsonCreator
    public Reservation(@JsonProperty("apartment") Apartment apartment, @JsonProperty("guest") Guest guest) {
        this.apartment = apartment;
        this.guest = guest;
        LOGGER.info("Reservation created: Apartment ID = {}, Guest = {}",
                apartment.getId(),
                guest.getName());
    }

    public void createReservation() {
        LOGGER.info("Attempting to create a reservation for Apartment ID = {}", apartment.getId());
        if (apartment.getStatus() == ApartmentStatus.AVAILABLE) {
            apartment.setStatus(ApartmentStatus.RESERVED);
            LOGGER.info("Reservation successful: Apartment ID = {} is now RESERVED", apartment.getId());
        } else {
            LOGGER.warn("Failed to reserve Apartment ID = {}: Already RESERVED", apartment.getId());
            throw new ApartmentAlreadyReservedException("Apartment is already reserved.");
        }
    }

    public void cancelReservation() {
        LOGGER.info("Attempting to cancel reservation for Apartment ID = {}", apartment.getId());
        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            apartment.setStatus(ApartmentStatus.AVAILABLE);
            LOGGER.info("Reservation cancelled: Apartment ID = {} is now AVAILABLE", apartment.getId());
        } else {
            LOGGER.warn("Failed to cancel reservation for Apartment ID = {}: Not RESERVED", apartment.getId());
            throw new ApartmentNotReservedException("Apartment is not reserved.");
        }
    }
}
