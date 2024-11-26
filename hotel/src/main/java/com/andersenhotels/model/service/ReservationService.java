package com.andersenhotels.model.service;

import com.andersenhotels.model.entities.Apartment;
import com.andersenhotels.model.entities.ApartmentStatus;
import com.andersenhotels.model.entities.Guest;
import com.andersenhotels.model.entities.Reservation;
import com.andersenhotels.model.storage.jpa.ApartmentRepository;
import com.andersenhotels.model.storage.jpa.GuestRepository;
import com.andersenhotels.model.storage.jpa.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final GuestRepository guestRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ApartmentRepository apartmentRepository,
                              GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.apartmentRepository = apartmentRepository;
        this.guestRepository = guestRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(long apartmentId, long guestId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new IllegalArgumentException("Apartment not found with ID: " + apartmentId));

        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            throw new IllegalStateException("Apartment is already reserved.");
        }

        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found with ID: " + guestId));

        apartment.setStatus(ApartmentStatus.RESERVED);
        apartmentRepository.save(apartment);

        Reservation reservation = new Reservation(apartment, guest);
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID: " + reservationId));

        Apartment apartment = reservation.getApartment();
        apartment.setStatus(ApartmentStatus.AVAILABLE);
        apartmentRepository.save(apartment);

        reservationRepository.deleteById(reservationId);
    }
}
