package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.ApartmentStatus;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import com.andersenhotels.presenter.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.presenter.exceptions.ApartmentNotFoundException;
import com.andersenhotels.presenter.exceptions.GuestNotFoundException;
import com.andersenhotels.presenter.exceptions.ReservationNotFoundException;
import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservationService extends AbstractCrudService<Reservation, Integer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

    public ReservationService() {
        super(Reservation.class);
    }

    public Reservation createReservation(Apartment apartment, Guest guest) throws
            ApartmentNotFoundException,
            GuestNotFoundException,
            ApartmentAlreadyReservedException,
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Attempting to create reservation for apartment ID {} and guest ID {}.",
                apartment.getId(), guest.getId());
        Reservation reservation = new Reservation(apartment, guest);
        Reservation savedReservation = create(reservation);
        updateApartmentStatus(apartment, ApartmentStatus.RESERVED);
        LOGGER.info("Reservation successfully created: {}", savedReservation);
        return savedReservation;
    }

    public void cancelReservation(Reservation reservation) throws
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Attempting to cancel reservation ID: {}", reservation.getId());
            if (reservation == null || !existsById(reservation.getId())) {
                throw new ReservationNotFoundException("Reservation does not exist or is invalid: " + reservation);
            }

            Apartment apartment = reservation.getApartment();
            updateApartmentStatus(apartment, ApartmentStatus.AVAILABLE);
            delete(reservation.getId());
    }

    private void updateApartmentStatus(Apartment apartment, ApartmentStatus status) throws
            IllegalArgumentException,
            PersistenceException {
        LOGGER.info("Updating apartment ID {} status to {}", apartment.getId(), status);
        apartment.setStatus(status);
        new ApartmentService().update(apartment);
        LOGGER.info("Apartment ID {} status successfully updated to {}", apartment.getId(), status);
    }
}
