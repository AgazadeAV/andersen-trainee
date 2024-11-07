package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;
import com.andersenhotels.presenter.exceptions.*;

import java.util.*;

/**
 * Service class that provides core functionalities for managing
 * hotel apartments, including apartment registration, reservation handling,
 * and pagination of available apartments.
 */
public class HotelService {
    // Defines the maximum number of apartments displayed per page.
    private static final int PAGE_SIZE = 5;

    private Map<Integer, Apartment> apartments;
    private Map<Integer, Reservation> reservations;
    private ValueValidator valueValidator;
    private int nextId;

    /**
     * Constructor that initializes the HotelService with empty apartment
     * and reservation lists and sets up a validator instance.
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
     * @param price The rental price for the apartment. Must be non-negative.
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
     * Returns the total number of apartments currently registered.
     *
     * @return The count of registered apartments.
     */
    public int getApartmentsCount() {
        return apartments.size();
    }

    /**
     * Returns the total number of apartments currently reserved.
     *
     * @return The count of reserved apartments.
     */
    public int getReservedApartmentsCount() {
        return reservations.size();
    }

    /**
     * Reserves an apartment for a specified guest by creating a reservation.
     *
     * @param id        The ID of the apartment to reserve.
     * @param guestName The name of the guest for the reservation.
     * @throws ApartmentNotFoundException if the apartment with the given ID does not exist.
     * @throws InvalidNameException       if the guest name is invalid.
     */
    public void reserveApartment(int id, String guestName) {
        valueValidator.validateApartmentId(id);
        valueValidator.validateGuestName(guestName);

        Apartment apartment = apartments.get(id);
        if (apartment == null) {
            throw new ApartmentNotFoundException("Apartment not found for the given ID. Please provide ID between 1 " +
                    "and " + getApartmentsCount() + ".");
        }

        Guest guest = new Guest(guestName);
        Reservation reservation = new Reservation(apartment, guest);
        reservation.createReservation();
        reservations.put(id, reservation);
    }

    /**
     * Releases a reserved apartment by canceling the reservation.
     *
     * @param id The ID of the apartment to release.
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
     * Lists a paginated view of registered apartments.
     *
     * @param page The page number to retrieve, starting from 1.
     * @return A list of apartments on the specified page.
     * @throws ApartmentNotFoundException if the page number is out of range or invalid.
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

    /**
     * Calculates the total number of pages available for apartment listings,
     * based on the defined PAGE_SIZE.
     *
     * @return The total number of pages for paginated apartment listings.
     */
    public int getTotalPages() {
        return (int) Math.ceil((double) apartments.size() / PAGE_SIZE);
    }
}
