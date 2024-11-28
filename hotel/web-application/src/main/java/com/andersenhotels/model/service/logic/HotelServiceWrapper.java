package com.andersenhotels.model.service.logic;

import com.andersenhotels.model.entities.*;
import com.andersenhotels.model.exceptions.ApartmentAlreadyReservedException;
import com.andersenhotels.model.exceptions.ApartmentNotFoundException;
import com.andersenhotels.model.exceptions.ReservationNotFoundException;
import com.andersenhotels.model.exceptions.WrongPageNumberException;
import com.andersenhotels.model.service.ApartmentService;
import com.andersenhotels.model.service.GuestService;
import com.andersenhotels.model.service.HotelService;
import com.andersenhotels.model.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelServiceWrapper {

    private final ApartmentService apartmentService;
    private final GuestService guestService;
    private final ReservationService reservationService;
    private final HotelService hotelService;

    @Value("${pagination.page-size}")
    private int pageSize;

    public Hotel initializeHotel() {
        return hotelService.getAllHotels().stream()
                .findFirst()
                .orElseGet(() -> {
                    Hotel newHotel = new Hotel();
                    return hotelService.createHotel(newHotel);
                });
    }

    public void registerApartment(double price, long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with ID: " + hotelId));
        Apartment apartment = new Apartment(price, hotel);
        apartmentService.registerApartment(apartment);
    }

    public void reserveApartment(long apartmentId, String guestName) {
        Apartment apartment = apartmentService.getApartmentById(apartmentId)
                .orElseThrow(() -> new ApartmentNotFoundException("Apartment not found with ID: " + apartmentId));

        if (apartment.getStatus() == ApartmentStatus.RESERVED) {
            throw new ApartmentAlreadyReservedException("Apartment is already reserved.");
        }

        Guest guest = guestService.getAllGuests().stream()
                .filter(g -> g.getName().equalsIgnoreCase(guestName))
                .findFirst()
                .orElseGet(() -> guestService.registerGuest(new Guest(guestName)));

        reservationService.createReservation(apartment.getId(), guest.getId());
    }

    public void releaseApartment(long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID: " + reservationId));

        reservationService.cancelReservation(reservation.getId());
    }

    public List<Apartment> listApartments(int page) {
        if (page <= 0) {
            throw new WrongPageNumberException("Page number must be greater than 0.");
        }

        return apartmentService.getAllApartments().stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .toList();
    }

    public int getTotalPages() {
        long totalApartments = apartmentService.getAllApartments().size();
        return (int) Math.ceil((double) totalApartments / pageSize);
    }
}
