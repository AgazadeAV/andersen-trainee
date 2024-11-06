package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import com.andersenhotels.presenter.exceptions.*;

import java.util.*;

/**
 * HotelService is a service class that manages hotel apartments, reservations,
 * and guests in a hotel management system. It provides functionality for
 * registering apartments, reserving apartments for guests, releasing apartments,
 * and listing apartments with pagination support.
 */
public class HotelService {
    private static final int PAGE_SIZE = 5;

    private Map<Integer, Apartment> apartments;
    private Map<Integer, Reservation> reservations;
    private ValueValidator valueValidator;
    private int nextId;

    /**
     * Constructs a new HotelService with empty maps for apartments and reservations
     * and initializes the next ID to 1.
     */
    public HotelService() {
        this.apartments = new HashMap<>();
        this.reservations = new HashMap<>();
        this.valueValidator = new ValueValidator(this);
        this.nextId = 1;
    }

    /**
     * Registers a new apartment with a specified price.
     *
     * @param price The price of the apartment. Must be positive.
     * @throws InvalidPriceException if the price is negative.
     */
    public void registerApartment(double price) {
        if (price < 0) {
            throw new InvalidPriceException("The price should be a positive number. Please try again.");
        }
        Apartment apartment = new Apartment(nextId++, price);
        apartments.put(apartment.getId(), apartment);
    }

    /**
     * Gets the total number of apartments registered in the hotel.
     *
     * @return The count of apartments.
     */
    public int getApartmentsCount() {
        return apartments.size();
    }

    /**
     * Reserves an apartment for a guest by apartment ID and guest name.
     *
     * @param id        The ID of the apartment to reserve.
     * @param guestName The name of the guest. Cannot be null or start with a digit.
     * @throws ApartmentNotFoundException if the apartment is not found.
     * @throws InvalidApartmentIdException if the apartment ID is invalid.
     * @throws InvalidNameException if the guest name is invalid.
     */
    public void reserveApartment(int id, String guestName) {
        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = apartments.get(id);
        if (apartment == null) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID.");
        }

        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation(apartment, guest);
        reservation.createReservation();
        reservations.put(id, reservation);
    }

    /**
     * Releases a reserved apartment by its ID. Removes the reservation if it exists.
     *
     * @param id The ID of the apartment to release.
     * @throws InvalidApartmentIdException if the apartment ID is invalid.
     * @throws ApartmentNotReservedException if the apartment is not currently reserved.
     */
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

    /**
     * Lists apartments with pagination support.
     *
     * @param page     The page number to retrieve.
     * @return A list of apartments for the specified page.
     * @throws ApartmentNotFoundException if the page or page size is invalid or no apartments are found.
     */
    public List<Apartment> listApartments(int page) {
        if (page <= 0 || PAGE_SIZE <= 0) {
            throw new ApartmentNotFoundException("Page number and page size must be greater than 0.");
        }

        List<Apartment> apartmentList = new ArrayList<>(apartments.values());

        if (page > getTotalPages()) {
            throw new ApartmentNotFoundException("No apartments found for the requested page number. " +
                    "Valid page numbers are from 1 to " + getTotalPages() + ".");
        }

        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, apartmentList.size());

        return apartmentList.subList(start, end);
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) apartments.size() / PAGE_SIZE);
    }
}
