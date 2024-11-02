package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;

import java.util.*;

/**
 * The HotelService class manages the registration, reservation,
 * and listing of apartments in the hotel.
 */
public class HotelService {
    private Map<Integer, Apartment> apartments;
    private int nextId;

    /**
     * Constructs a new HotelService instance with an empty apartment registry
     * and initializes the next available apartment ID.
     */
    public HotelService() {
        apartments = new HashMap<>();
        nextId = 1;
    }

    /**
     * Registers a new apartment with the specified price.
     *
     * @param price the price of the apartment.
     */
    public void registerApartment(double price) {
        Apartment apartment = new Apartment(nextId++, price);
        apartments.put(apartment.getId(), apartment);
        System.out.println("Apartment registered: " + apartment);
    }

    /**
     * Returns the total count of registered apartments.
     *
     * @return the number of apartments.
     */
    public int getApartmentsCount() {
        return apartments.size();
    }

    /**
     * Reserves an apartment for a guest with the specified name.
     *
     * @param id the ID of the apartment to be reserved.
     * @param guestName the name of the guest reserving the apartment.
     */
    public void reserveApartment(int id, String guestName) {
        Apartment apartment = apartments.get(id);
        if (apartment != null) {
            Guest guest = new Guest(guestName);
            Reservation reservation = new Reservation(apartment, guest);
            try {
                reservation.createReservation();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Apartment not found.");
        }
    }

    /**
     * Releases the reservation of an apartment by its ID.
     *
     * @param id the ID of the apartment to be released.
     */
    public void releaseApartment(int id) {
        Apartment apartment = apartments.get(id);
        if (apartment != null) {
            Guest guest = new Guest("");
            Reservation reservation = new Reservation(apartment, guest);
            try {
                reservation.cancelReservation();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Apartment not found.");
        }
    }

    /**
     * Lists the apartments on the specified page with a defined page size.
     *
     * @param page the page number to display.
     * @param pageSize the number of apartments to display per page.
     */
    public void listApartments(int page, int pageSize) {
        List<Apartment> apartmentList = new ArrayList<>(apartments.values());
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, apartmentList.size());

        if (start >= apartmentList.size() || start < 0) {
            System.out.println("No apartments found on this page.");
            return;
        }

        for (int i = start; i < end; i++) {
            System.out.println(apartmentList.get(i));
        }
    }
}
