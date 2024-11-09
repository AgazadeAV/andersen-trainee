package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import com.andersenhotels.presenter.exceptions.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class HotelService {
    private Map<Integer, Apartment> apartments;
    private Map<Integer, Reservation> reservations;
    private int nextId;

    @JsonIgnore
    private ValueValidator valueValidator;

    private static final int PAGE_SIZE = 5;

    public HotelService() {
        this.apartments = new HashMap<>();
        this.reservations = new HashMap<>();
        this.valueValidator = new ValueValidator(this);
        this.nextId = 1;
    }

    public int apartmentsCount() {
        return apartments.size();
    }

    public int reservedApartmentsCount() {
        return reservations.size();
    }

    @JsonIgnore
    public int totalPages() {
        return (int) Math.ceil((double) apartments.size() / PAGE_SIZE);
    }

    public void registerApartment(double price) {
        if (price < 0) {
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }
        Apartment apartment = new Apartment(nextId++, price);
        apartments.put(apartment.getId(), apartment);
    }

    public void reserveApartment(int id, String guestName) {
        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = apartments.get(id);
        if (apartment == null) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Please provide ID between 1 " +
                    "and " + apartmentsCount() + ".");
        }

        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation(apartment, guest);
        reservation.createReservation();
        reservations.put(id, reservation);
    }

    public void releaseApartment(int id) {
        valueValidator.validateApartmentId(id);
        Reservation reservation = reservations.get(id);

        if (reservation != null) {
            reservation.cancelReservation();
            reservations.remove(id);
        } else {
            throw new ApartmentNotReservedException("Apartment is not reserved. Please try again.");
        }
    }

    public List<Apartment> listApartments(int page) {
        if (page <= 0 || PAGE_SIZE <= 0) {
            throw new ApartmentNotFoundException("Page number and page size must be greater than 0.");
        }

        if (page > totalPages()) {
            throw new ApartmentNotFoundException("No apartments found for the requested page number. " +
                    "Valid page numbers are from 1 to " + totalPages() + ".");
        }

        return apartments.values().stream()
                .sorted(Comparator.comparingInt(Apartment::getId))
                .skip((long) (page - 1) * PAGE_SIZE)
                .limit(PAGE_SIZE)
                .toList();
    }
}
