package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.ApartmentStatus;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservationService extends AbstractCrudService<Reservation, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService() {
        super(Reservation.class);
    }

    public Reservation createReservation(Apartment apartment, Guest guest) {
        LOGGER.info("Attempting to create reservation for apartment ID {} and guest ID {}.",
                apartment.getId(), guest.getId());
        try {
            Reservation reservation = new Reservation(apartment, guest);
            Reservation savedReservation = create(reservation);
            updateApartmentStatus(apartment, ApartmentStatus.RESERVED);
            LOGGER.info("Reservation successfully created: {}", savedReservation);
            return savedReservation;
        } catch (Exception e) {
            LOGGER.error("Failed to create reservation for apartment ID {} and guest ID {}: {}",
                    apartment.getId(), guest.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public void cancelReservation(Reservation reservation) {
        LOGGER.info("Attempting to cancel reservation ID: {}", reservation != null ? reservation.getId() : "null");
        try {
            if (reservation == null || !existsById(reservation.getId())) {
                throw new IllegalArgumentException("Reservation does not exist or is invalid: " + reservation);
            }

            Apartment apartment = reservation.getApartment();
            updateApartmentStatus(apartment, ApartmentStatus.AVAILABLE);
            delete(reservation.getId());
            LOGGER.info("Reservation successfully canceled with ID: {}", reservation.getId());
        } catch (Exception e) {
            LOGGER.error("Failed to cancel reservation ID: {}: {}",
                    reservation != null ? reservation.getId() : "null", e.getMessage(), e);
            throw e;
        }
    }

    private void updateApartmentStatus(Apartment apartment, ApartmentStatus status) {
        LOGGER.info("Updating apartment ID {} status to {}", apartment.getId(), status);
        try {
            apartment.setStatus(status);
            new ApartmentService().update(apartment);
            LOGGER.info("Apartment ID {} status successfully updated to {}", apartment.getId(), status);
        } catch (Exception e) {
            LOGGER.error("Failed to update apartment ID {} status to {}: {}", apartment.getId(), status, e.getMessage(), e);
            throw e;
        }
    }
}
