package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.ApartmentStatus;
import com.andersenhotels.model.entities.Guest;
import com.andersenhotels.model.entities.Reservation;
import com.andersenhotels.model.storage.jpa.ApartmentRepository;
import com.andersenhotels.model.storage.jpa.GuestRepository;
import com.andersenhotels.model.storage.jpa.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final GuestRepository guestRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(long apartmentId, long guestId) {
        try {
            Apartment apartment = apartmentRepository.findById(apartmentId)
                    .orElseThrow(() -> new IllegalArgumentException("Apartment not found with ID: " + apartmentId));

            if (apartment.getStatus() == ApartmentStatus.RESERVED) {
                throw new IllegalStateException("Apartment is already reserved.");
            }

            Guest guest = guestRepository.findById(guestId)
                    .orElseThrow(() -> new IllegalArgumentException("Guest not found with ID: " + guestId));

            Reservation reservation = new Reservation(apartment, guest);
            apartment.setStatus(ApartmentStatus.RESERVED);
            apartmentRepository.save(apartment);

            return reservationRepository.save(reservation);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while creating the reservation.", e);
        }
    }


    public boolean cancelReservation(long reservationId) {
        try {
            Reservation reservation = reservationRepository.findById(reservationId)
                    .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

            Apartment apartment = reservation.getApartment();

            if (apartment.getStatus() != ApartmentStatus.RESERVED) {
                throw new IllegalStateException("Apartment is not reserved.");
            }

            apartment.setStatus(ApartmentStatus.AVAILABLE);
            apartmentRepository.save(apartment);

            reservationRepository.deleteById(reservationId);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while canceling the reservation.", e);
        }
    }
}
