package com.andersenhotels.model.service.logic;

import com.andersenhotels.model.*;
import com.andersenhotels.model.service.ApartmentService;
import com.andersenhotels.model.service.GuestService;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.service.ReservationService;

import java.util.List;

public class HotelServiceWrapper {

    private final ApartmentService apartmentService;
    private final GuestService guestService;
    private final ReservationService reservationService;
    private final HotelService hotelService;

    public HotelServiceWrapper() {
        this.apartmentService = new ApartmentService();
        this.guestService = new GuestService();
        this.reservationService = new ReservationService();
        this.hotelService = new HotelService();
    }

    public Hotel initializeHotel() {
        return hotelService.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    Hotel newHotel = new Hotel();
                    hotelService.create(newHotel);
                    System.out.println("No existing hotel found. A new hotel has been created.");
                    return newHotel;
                });
    }

    public void registerApartment(double price, Hotel hotel) {
        apartmentService.registerApartment(price, hotel);
    }

    public void reserveApartment(Hotel hotel, int apartmentId, String guestName) {
        Apartment apartment = apartmentService.read(apartmentId)
                .orElseThrow(() -> new IllegalArgumentException("Apartment not found for the given ID."));

        Guest guest = guestService.registerGuest(guestName);
        Reservation reservation = reservationService.createReservation(apartment, guest);

        apartmentService.update(apartment);
        hotel.getReservations().add(reservation);
    }

    public void releaseApartment(Hotel hotel, int reservationId) {
        Reservation reservation = reservationService.read(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found for the given ID."));

        reservationService.cancelReservation(reservation);
        hotel.getReservations().remove(reservation);
    }

    public int getApartmentsCount() {
        return apartmentService.findAll().size();
    }

    public List<Apartment> listApartments(int page, int pageSize) {
        return apartmentService.findAll().stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }
}
