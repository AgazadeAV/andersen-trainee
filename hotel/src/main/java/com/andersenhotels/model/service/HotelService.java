package com.andersenhotels.model.service;

import com.andersenhotels.model.config.ConfigManager;
import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.ApartmentStatus;
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
    private int nextApartmentId;

    @JsonIgnore
    private ValueValidator valueValidator;

    public HotelService() {
        this.apartments = new HashMap<>();
        this.reservations = new HashMap<>();
        this.valueValidator = new ValueValidator(this);
        this.nextApartmentId = 1;
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
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }
        Apartment apartment = new Apartment(nextApartmentId++, price);
        apartments.put(apartment.getId(), apartment);
    }

    public void reserveApartment(int id, String guestName) {
        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = Optional.ofNullable(apartments.get(id))
                .orElseThrow(() -> new ApartmentNotFoundException(
                        "Apartment not found for the given ID. Please provide ID between 1 and " + apartmentsCount() + "."
                ));

        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation(apartment, guest);
        reservation.createReservation();
        reservations.put(id, reservation);
    }

    public void releaseApartment(int id) {
        valueValidator.validateApartmentId(id);

        Reservation reservation = Optional.ofNullable(reservations.get(id))
                .orElseThrow(() -> new ApartmentNotReservedException("Apartment is not reserved. Please try again."));

        reservation.cancelReservation();
        reservations.remove(id);
    }

    public List<Apartment> listApartments(int page) {
        if (page <= 0 || ConfigManager.getPageSizeForPagination() <= 0) {
            throw new ApartmentNotFoundException("Page number and page size must be greater than 0.");
        }

        if (page > totalPages()) {
            throw new ApartmentNotFoundException("No apartments found for the requested page number. " +
                    "Valid page numbers are from 1 to " + totalPages() + ".");
        }

        return apartments.values().stream()
                .sorted(Comparator.comparingInt(Apartment::getId))
                .skip((long) (page - 1) * ConfigManager.getPageSizeForPagination())
                .limit(ConfigManager.getPageSizeForPagination())
                .toList();
    }

    public void changeApartmentStatus(int apartmentId, ApartmentStatus newStatus) {
        if (!ConfigManager.isAllowApartmentStatusChange()) {
            throw new UnsupportedOperationException("Changing apartment status is disabled by configuration.");
        }

        Apartment apartment = Optional.ofNullable(apartments.get(apartmentId))
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment not found for the given ID."));

        apartment.setStatus(newStatus);
    }
}
