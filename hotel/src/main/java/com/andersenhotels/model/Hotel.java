package com.andersenhotels.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "hotels")
@Setter
@Getter
public class Hotel {

    private static final Logger LOGGER = LoggerFactory.getLogger(Hotel.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private List<Apartment> apartments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private List<Reservation> reservations;

    public Hotel() {
        this.apartments = new ArrayList<>();
        this.reservations = new ArrayList<>();
        LOGGER.info("Hotel initialized");
    }

    public void addApartment(Apartment apartment) {
        LOGGER.info("Attempting to add apartment with ID = {}", apartment.getId());
        if (apartments.stream().anyMatch(apartmentForCheck -> apartmentForCheck.getId() == apartment.getId())) {
            throw new IllegalStateException("Hotel already has apartment with ID = " + apartment.getId());
        }
        apartments.add(apartment);
        LOGGER.info("Apartment added successfully: ID = {}, Details = {}", apartment.getId(), apartment);
    }

    public void addReservation(Reservation reservation) {
        LOGGER.info("Attempting to add reservation for Apartment ID = {}", reservation.getApartment().getId());
        if (reservations.stream().anyMatch(reservationForCheck ->
                reservationForCheck.getApartment().getId() == reservation.getApartment().getId())) {
            throw new IllegalStateException("Reservation for apartment ID " +
                    reservation.getApartment().getId() + " already exists.");
        }
        reservations.add(reservation);
        LOGGER.info("Reservation added successfully: Apartment ID = {}, Guest = {}",
                reservation.getApartment().getId(), reservation.getGuest().getName());
    }

    public void removeReservation(int apartmentId) {
        LOGGER.info("Attempting to remove reservation for Apartment ID = {}", apartmentId);
        Reservation reservation = reservations.stream()
                .filter(reservationForCheck -> reservationForCheck.getApartment().getId() == apartmentId)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Reservation for apartment ID " + apartmentId + " does not exist."));

        reservations.remove(reservation);
        LOGGER.info("Reservation removed successfully for Apartment ID = {}", apartmentId);
    }

    public Map<Integer, Apartment> getApartmentMap() {
        Map<Integer, Apartment> map = new HashMap<>();
        for (Apartment apartment : apartments) {
            map.put(apartment.getId(), apartment);
        }
        return map;
    }

    public Map<Integer, Reservation> getReservationMap() {
        Map<Integer, Reservation> map = new HashMap<>();
        for (Reservation reservation : reservations) {
            map.put(reservation.getApartment().getId(), reservation);
        }
        return map;
    }
}
