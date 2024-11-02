package model;

import java.util.*;

public class Hotel {
    private List<Apartment> apartments;
    private int nextId;

    public Hotel() {
        apartments = new ArrayList<>();
        nextId = 1;
    }

    public void registerApartment(double price) {
        Apartment apartment = new Apartment(nextId++, price);
        apartments.add(apartment);
        System.out.println("Apartment registered: " + apartment);
    }

    public int getApartmentsCount() {
        return apartments.size();
    }

    public void reserveApartment(int id, String clientName) {
        for (Apartment apartment : apartments) {
            if (apartment.getId() == id) {
                if (!apartment.isReserved()) {
                    apartment.reserve(clientName);
                    System.out.println("Apartment reserved for " + clientName);
                } else {
                    System.out.println("Apartment is already reserved.");
                }
                return;
            }
        }
    }

    public void releaseApartment(int id) {
        for (Apartment apartment : apartments) {
            if (apartment.getId() == id) {
                if (apartment.isReserved()) {
                    apartment.release();
                    System.out.println("Apartment released.");
                } else {
                    System.out.println("Apartment is not reserved.");
                }
                return;
            }
        }
    }

    public void listApartments(int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, apartments.size());

        if (start >= apartments.size() || start < 0) {
            System.out.println("No apartments found on this page.");
            return;
        }

        for (int i = start; i < end; i++) {
            System.out.println(apartments.get(i));
        }
    }
}
