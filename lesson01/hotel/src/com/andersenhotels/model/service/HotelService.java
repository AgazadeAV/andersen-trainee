package com.andersenhotels.model.service;

import com.andersenhotels.model.Apartment;
import com.andersenhotels.model.Guest;
import com.andersenhotels.model.Reservation;

import java.util.*;

public class HotelService {
    private Map<Integer, Apartment> apartments;
    private int nextId;

    public HotelService() {
        apartments = new HashMap<>();
        nextId = 1;
    }

    public void registerApartment(double price) {
        Apartment apartment = new Apartment(nextId++, price);
        apartments.put(apartment.getId(), apartment);
        System.out.println("Apartment registered: " + apartment);
    }

    public int getApartmentsCount() {
        return apartments.size();
    }

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
